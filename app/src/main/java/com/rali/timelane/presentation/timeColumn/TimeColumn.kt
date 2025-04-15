package com.rali.timelane.presentation.timeColumn

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.rali.checkyourlife.Activity
import com.rali.checkyourlife.ActivityBlock
import com.rali.checkyourlife.toMinutes

@Composable
fun TimeColumn(hour: Int, scheduledActivities: List<Activity>?) {
    Box(Modifier.fillMaxSize()) {
        val activitiesForHour = scheduledActivities?.filter { it.startHour == hour }
        val nextActivity = scheduledActivities?.filter { it.startHour > hour }?.minByOrNull { it.endTime + it.endMiniute.toFloat()/60 }
        val activitiesForMiddleHour = scheduledActivities?.filter {it.startHour < hour && ((it.endHour + it.endMiniute.toFloat()/60) > hour)}

        if (((activitiesForHour == null || activitiesForMiddleHour == null))
            || (activitiesForHour.isEmpty()
            && activitiesForMiddleHour.isEmpty())) {
            // 🔹 활동이 없을 경우 빈 박스를 추가
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), // 원하는 기본 높이 지정
            )
        } else if (activitiesForHour != null) {
            if (activitiesForHour.isNotEmpty()) {
                activitiesForHour.forEach { activity ->
                    val (offset, height) = calculateActivityOffsetAndHeight(activity)
                    Column {
                        val latestActivity = activitiesForMiddleHour.maxByOrNull { it.endTime + it.endMiniute / 60.0 }
                        val emptyBoxHeightTop =
                            if (latestActivity == null)
                                    (activity.startMinute).mod(60).dp
                            else activity.startMinute.dp - latestActivity.endMiniute.dp

                        Box(
                            modifier = Modifier.fillMaxWidth().height(emptyBoxHeightTop)
                        )
                        ActivityBlock(
                            activity,
                            Modifier
                                .fillMaxWidth()
                                .height(height)  // 활동의 길이 설정
                            //.offset(y = offset) // 시작 위치 조정
                        )

                        //val nextActivity = activitiesForMiddleHour.maxByOrNull { it.endTime + it.endMiniute / 60.0 }

                        val emptyBoxHeightBottom =
                            if (nextActivity != null && (nextActivity.startHour == activity.endHour) && activity.endMiniute != 0)
                                    (nextActivity.startMinute-activity.endMiniute).dp
                            else (60-activity.endMiniute).mod(60).dp

                        Box(
                            modifier = Modifier.fillMaxWidth().height(emptyBoxHeightBottom)
                        )
                    }

                }

            }
        }
    }
}

fun calculateActivityOffsetAndHeight(activity: Activity): Pair<Dp, Dp> {
    val startMinutes = activity.startTime.toMinutes() % 60
    val durationMinutes = activity.durationMinutes()

    val offset = (startMinutes / 60f) * 60.dp  // 분을 높이 비율로 변환
    val height = (durationMinutes / 60f) * 60.dp // 지속 시간도 높이로 변환

    Log.i("height", "${activity.title}:${offset}")
    return Pair(offset, height)
}