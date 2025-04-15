package com.rali.timelane.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rali.checkyourlife.Activity
import com.rali.timelane.domain.dao.ActivityDao

@Database(entities = [Activity::class], version = 2, exportSchema = false)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getDatabase(context: Context): ActivityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivityDatabase::class.java,
                    "activity_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}