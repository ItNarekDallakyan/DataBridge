package narek.dallakyan.common.ext

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

const val DATA_LINK_URI = "content://narek.dallakyan.datalink.dataProvider/data_store"

fun Context.writeDataLink(key: String, value: String? = "") {
    try {
        val uri = Uri.parse(DATA_LINK_URI)
        val values = ContentValues().apply {
            put("key", key)
            put("value", value)
        }
        this.contentResolver.insert(uri, values)
    } catch (error: Throwable) {
        error.printStackTrace()
    }
}

fun Context.readDataLink(key: String): String? {
    try {
        val uri = Uri.parse(DATA_LINK_URI)
        val projection = arrayOf("value")
        val selection = "key = ?"
        val selectionArgs = arrayOf(key)
        val cursor: Cursor? = this.contentResolver.query(
            uri, projection, selection, selectionArgs, null
        )
        var result: String? = null
        cursor?.use { cur ->
            if (cur.moveToFirst()) {
                result = cur.getString(cur.getColumnIndexOrThrow("value"))
            }
        }
        return result
    } catch (error: Throwable) {
        error.printStackTrace()
        return null
    }
}

fun Context.deleteData(key: String): Int? {
    return try {
        val uri = Uri.parse(DATA_LINK_URI)
        val selectionArgs = arrayOf(key)
        contentResolver.delete(
            uri, null, selectionArgs
        )
    } catch (error: Throwable) {
        error.printStackTrace()
        null
    }
}

fun Context.deleteAllData(): Int? {
    return try {
        val uri = Uri.parse(DATA_LINK_URI)
        contentResolver.delete(
            uri, null, null
        )
    } catch (error: Throwable) {
        error.printStackTrace()
        null
    }
}



