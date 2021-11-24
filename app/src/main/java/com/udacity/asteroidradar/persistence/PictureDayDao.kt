package com.udacity.asteroidradar.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.models.PictureOfDay

@Dao
interface PictureDayDao {
    @Query("SELECT * FROM PictureOfDay")
    fun get(): LiveData<PictureOfDay>

    @Transaction
    fun updateData(pic: PictureOfDay): Long {
        deleteAll()
        return insert(pic)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pic: PictureOfDay): Long

    @Query("DELETE FROM PictureOfDay")
    fun deleteAll()
}