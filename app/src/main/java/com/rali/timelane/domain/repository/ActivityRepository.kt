package com.rali.timelane.domain.repository

import com.rali.checkyourlife.Activity
import com.rali.timelane.domain.dao.ActivityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityDao: ActivityDao
) {
    // 활동 추가
    suspend fun insertActivity(activity: Activity) {
        activityDao.insertActivity(activity)
    }

    // 여러 활동 한번에 추가
    suspend fun insertActivities(activities: List<Activity>) {
        activityDao.insertActivities(activities)
    }

    // 계획된 활동 가져오기
    suspend fun getPlannedActivities(): Flow<List<Activity>> {
        return activityDao.getPlannedActivities().flowOn(Dispatchers.IO).conflate()
    }

    // 실제 활동 가져오기
    suspend fun getActualActivities(): Flow<List<Activity>> {
        return activityDao.getActualActivities().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun updateActivity(activity: Activity) {
        activityDao.updateActivity(activity)
    }

    // 활동 삭제
    suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }

    suspend fun deleteAllActivites() {
        activityDao.deleteAllActivites()
    }

    suspend fun deletePlannedActivitiesByDate(date: Long) {
        activityDao.deletePlannedActivitiesByDate(date)
    }
}
