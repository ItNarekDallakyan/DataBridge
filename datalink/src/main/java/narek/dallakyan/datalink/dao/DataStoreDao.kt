package narek.dallakyan.datalink.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import narek.dallakyan.datalink.entity.DataStoreEntity

@Dao
interface DataStoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataStoreEntity: DataStoreEntity): Long

    @Query("SELECT * FROM data_store WHERE `key` = :key LIMIT 1")
    suspend fun getDataByKey(key: String): DataStoreEntity?

    @Update
    suspend fun update(dataStoreEntity: DataStoreEntity)

    @Delete
    suspend fun delete(dataStoreEntity: DataStoreEntity)

    @Query("DELETE FROM data_store WHERE `key` = :key")
    suspend fun deleteByKey(key: String)
}
