package com.example.checkyourlife

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun TimeColumn(hour: Int, scheduledActivities: List<Activity>?) {
    Box(Modifier.fillMaxSize()) {
        val activitiesForHour = scheduledActivities?.filter { it.startHour == hour }
        val activitiesForMiddleHour = scheduledActivities?.filter {it.startHour < hour && ((it.endHour + it.endMiniute.toFloat()/60) > hour)}

        if (((activitiesForHour == null || activitiesForMiddleHour == null))
            || (activitiesForHour.isEmpty()
            && activitiesForMiddleHour.isEmpty())) {
            // 🔹 활동이 없을 경우 빈 박스를 추가
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), // 원하는 기본 높이 지정
                //contentAlignment = Alignment.Center
            )
//            {
//                Text(text = "No Activity", color = Color.Gray, fontSize = 12.sp)
//            }
        } else if (activitiesForHour != null) {
            if (activitiesForHour.isNotEmpty()) {
                Divider()
                activitiesForHour.forEach { activity ->
                    val (offset, height) = calculateActivityOffsetAndHeight(activity)
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth().height((activity.startMinute).mod(60).dp)
                        )
                        //{ Text("Upper Remainder", fontSize = 12.sp) }
                        ActivityBlock(
                            activity,
                            Modifier
                                .fillMaxWidth()
                                .height(height)  // 활동의 길이 설정
                            //.offset(y = offset) // 시작 위치 조정
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth().height((60-activity.endMiniute).mod(60).dp)
                        )
                        //{ Text("Lower Remainder", fontSize = 12.sp) }
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

    return Pair(offset, height)
}