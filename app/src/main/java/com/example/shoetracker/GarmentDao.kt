package com.example.shoetracker



import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GarmentDao {
    @Insert
    suspend fun insert(item: GarmentItem)

    @Delete
    suspend fun delete(item: GarmentItem)

    @Query("SELECT * FROM garment_items ORDER BY lastCleanedDate ASC")
    fun getAllItems(): Flow<List<GarmentItem>>
}