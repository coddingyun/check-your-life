package com.example.checkyourlife

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(activity: Activity)

    @Query("SELECT * FROM activities WHERE type = 'planned'")
    fun getPlannedActivities(): List<Activity>

    @Query("SELECT * FROM activities WHERE type = 'actual'")
    fun getActualActivities(): List<Activity>

    @Delete
    fun deleteActivity(activity: Activity)

    @Query("DELETE FROM activities WHERE id = :activityId")
    fun deleteActivityById(activityId: Long)
}