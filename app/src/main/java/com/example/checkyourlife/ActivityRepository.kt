package com.example.checkyourlife

import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityDao: ActivityDao
) {
    // 활동 추가
    suspend fun insertActivity(activity: Activity) {
        activityDao.insertActivity(activity)
    }

    // 계획된 활동 가져오기
    fun getPlannedActivities(): List<Activity> {
        return activityDao.getPlannedActivities()
    }

    // 실제 활동 가져오기
    fun getActualActivities(): List<Activity> {
        return activityDao.getActualActivities()
    }

    // 활동 삭제
    suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }
}
