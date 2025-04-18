package narek.dallakyan.datalink.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import narek.dallakyan.datalink.DataStoreDatabase
import narek.dallakyan.datalink.dao.DataStoreDao
import narek.dallakyan.datalink.entity.DataStoreEntity

class DataStoreContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "narek.dallakyan.datalink.dataProvider"
        private const val DIR_PATH = "data_store"
        private const val CODE_DIR = 1
        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, DIR_PATH, CODE_DIR)
        }

    }

    private lateinit var dataStoreDao: DataStoreDao

    override fun onCreate(): Boolean {
        context?.let {
            val database =
                Room.databaseBuilder(it, DataStoreDatabase::class.java, "data_store_db").build()
            dataStoreDao = database.dataStoreDao()
            return true
        }
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (MATCHER.match(uri)) {
            CODE_DIR -> {
                val cursor = MatrixCursor(arrayOf("key", "value"))
                selectionArgs?.firstOrNull()?.let { key ->
                    val result = runBlocking(Dispatchers.IO) {
                        dataStoreDao.getDataByKey(key)
                    }
                    result?.let {
                        cursor.addRow(arrayOf(it.key, it.value))
                    }
                }
                cursor
            }

            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (MATCHER.match(uri) == CODE_DIR && values != null) {
            val key = values.getAsString("key") ?: return null
            val value = values.getAsString("value") ?: return null
            val entity = DataStoreEntity(key = key, value = value)
            val id = runBlocking { dataStoreDao.insert(entity) }
            ContentUris.withAppendedId(uri, id)
        }
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return if (MATCHER.match(uri) == CODE_DIR && selectionArgs != null) {
            val key = selectionArgs.firstOrNull() ?: return 0
            val rowsUpdated = runBlocking {
                val entity = dataStoreDao.getDataByKey(key)
                entity?.let {
                    values?.let {
                        it.getAsString("value")?.let { value ->
                            entity.value = value
                        }
                    }
                    dataStoreDao.update(entity)
                    1
                } ?: 0
            }
            rowsUpdated
        } else {
            0
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return if (MATCHER.match(uri) == CODE_DIR && selectionArgs != null) {
            val key = selectionArgs.firstOrNull() ?: return 0
            val rowsDeleted = runBlocking {
                dataStoreDao.deleteByKey(key)
                1
            }
            rowsDeleted
        } else {
            0
        }
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_DIR -> "vnd.android.cursor.dir/vnd.narek.dallakyan.data"
            else -> null
        }
    }
}

