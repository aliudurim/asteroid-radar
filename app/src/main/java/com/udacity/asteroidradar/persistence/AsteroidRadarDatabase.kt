package com.udacity.asteroidradar.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay

private const val DATABASE = "asteroid_radar_db"

@Database(
    entities = [
        Asteroid::class,
        PictureOfDay::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AsteroidRadarDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao
    abstract fun pictureDayDao(): PictureDayDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AsteroidRadarDatabase? = null

        fun getInstance(context: Context): AsteroidRadarDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        fun clear() {
            instance?.clearAllTables()
        }

        private fun buildDatabase(context: Context): AsteroidRadarDatabase {
            return Room.databaseBuilder(context, AsteroidRadarDatabase::class.java, DATABASE)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}