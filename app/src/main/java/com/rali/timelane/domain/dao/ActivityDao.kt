package com.rali.timelane.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rali.checkyourlife.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<Activity>)

    @Query("SELECT * FROM activities WHERE type = 'PLAN'")
    fun getPlannedActivities(): Flow<List<Activity>>

    @Query("SELECT * FROM activities WHERE type = 'REALITY'")
    fun getActualActivities(): Flow<List<Activity>>

    @Update
    suspend fun updateActivity(activity: Activity)

    @Delete
    fun deleteActivity(activity: Activity)

    @Query("DELETE FROM activities")
    fun deleteAllActivites()

    @Query("DELETE FROM activities WHERE id = :activityId")
    fun deleteActivityById(activityId: Long)

    @Query("DELETE FROM activities WHERE type = 'PLAN' AND date = :date")
    fun deletePlannedActivitiesByDate(date: Long)
}