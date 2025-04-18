package narek.dallakyan.datalink.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "data_store",
    indices = [Index(value = ["key"], unique = true)]
)
data class DataStoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "value") var value: String
)
