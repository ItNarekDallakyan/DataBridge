package narek.dallakyan.datalink

import androidx.room.Database
import androidx.room.RoomDatabase
import narek.dallakyan.datalink.dao.DataStoreDao
import narek.dallakyan.datalink.entity.DataStoreEntity

@Database(entities = [DataStoreEntity::class], version = 1, exportSchema = false)
abstract class DataStoreDatabase : RoomDatabase() {
    abstract fun dataStoreDao(): DataStoreDao
}
