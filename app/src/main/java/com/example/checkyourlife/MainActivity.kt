package com.example.checkyourlife

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkyourlife.ui.theme.CheckYourLifeTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckYourLifeTheme {
                DailyPlannerApp()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyPlannerApp() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    val scheduledActivities = remember {
        listOf(
            Activity(1, "Morning Workout", "06:00", "07:00", Color(0xFF2196F3)),
            Activity(2, "Team Meeting", "10:00", "11:30", Color(0xFF9C27B0)),
            Activity(3, "Lunch Break", "12:30", "13:30", Color(0xFF4CAF50)),
            Activity(4, "Project Work", "14:00", "17:00", Color(0xFFFFC107)),
            Activity(5, "Evening Run", "18:30", "19:30", Color(0xFFE91E63))
        )
    }

    val actualActivities = remember {
        listOf(
            Activity(1, "Morning Workout", "06:30", "07:15", Color(0xFF2196F3)),
            Activity(2, "Team Meeting", "10:15", "12:00", Color(0xFF9C27B0)),
            Activity(3, "Lunch Break", "12:30", "14:00", Color(0xFF4CAF50)),
            Activity(4, "Project Work", "14:30", "16:45", Color(0xFFFFC107)),
            Activity(5, "Evening Run", "19:00", "20:00", Color(0xFFE91E63))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                actions = {
                    IconButton(onClick = { currentDate = currentDate.minusDays(1) }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Day")
                    }

                    // 가운데 위치하도록 하기 위해 Modifier.weight(1f)를 사용
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),  // Box가 높이를 채우도록 함
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sat, Mar 1",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(onClick = { currentDate = currentDate.plusDays(1) }) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Day")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .drawBehind {
                    val timeX = 48.dp.toPx()
                    drawLine(
                        color = Color.Gray,
                        start = Offset(timeX, 0f),
                        end = Offset(timeX, size.height),
                        strokeWidth = 1.dp.toPx()
                    )

                    val centerX = size.width / 2 + 24.dp.toPx() // 중앙 위치 계산
                    drawLine(
                        color = Color.Gray, // 선 색상
                        start = Offset(centerX, 0f), // 위쪽 시작점
                        end = Offset(centerX, size.height), // 아래쪽 끝점
                        strokeWidth = 1.dp.toPx() // 선 두께
                    )
                }
        ) {
            // Plan and Reality columns
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.width(48.dp))
                // Plan column header
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Plan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray
                            )
                        }
                    }
                }

                // Reality column header
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Reality",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Track",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
            TimelineContent(scheduledActivities, actualActivities)
        }
    }
}

@Composable
fun TimelineContent(scheduledActivities: List<Activity>, actualActivities: List<Activity>) {
    Box(Modifier.fillMaxSize()) {
        // Column 안에 시간별 활동을 나열
        LazyColumn {
            items((0..23).toList()) { hour ->
                TimelineRow(hour, scheduledActivities, actualActivities)
            }

        }



//        actualActivities.forEach { activity ->
//            val (offset, height) = calculateActivityOffsetAndHeight(activity)
//            Box(Modifier
//                .fillMaxWidth()
//                .height(height)  // 활동의 길이 설정
//                .offset(y = offset) // 시작 위치 조정
//            ) {
//                ActivityBlock(
//                    activity,
//                    Modifier.fillMaxSize()  // 전체 크기로 ActivityBlock 표시
//                )
//            }
//        }
    }
}


@Composable
fun TimelineRow(hour: Int, scheduledActivities: List<Activity>, actualActivities: List<Activity>) {
    Row(Modifier.height(60.dp)) {  // Row의 높이를 고정하지 않고 유동적으로 설정
        // 🕒 시간 표시 영역
        Box(Modifier.width(48.dp)) {
            Text(
                text = String.format("%02d:00", hour),
                Modifier.align(Alignment.CenterStart),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // 🗓️ 일정 (계획된 활동)
        Box(Modifier.weight(1f)) {
            Divider(Modifier.align(Alignment.TopCenter).zIndex(-1f)) // Divider를 60.dp마다 고정
            Box(Modifier.fillMaxHeight()) {
                scheduledActivities.filter { it.startHour == hour }.forEach { activity ->
                    val (offset, height) = calculateActivityOffsetAndHeight(activity)
                    ActivityBlock(
                        activity,
                        Modifier
                            .fillMaxWidth()
                            .height(height)  // 활동의 길이 설정
                            .offset(y = offset) // 시작 위치 조정
                    )
                }
            }
        }

        // 📌 실제 활동
        Box(Modifier.weight(1f)) {
            Divider(Modifier.align(Alignment.TopCenter)) // Divider를 60.dp마다 고정
            Box() {
                actualActivities.filter { it.startHour == hour }.forEach { activity ->
                    val (offset, height) = calculateActivityOffsetAndHeight(activity)
                    ActivityBlock(
                        activity,
                        Modifier
                            .fillMaxWidth()
                            .height(height)  // 활동의 길이 설정
                            .offset(offset) // 시작 위치 조정
                    )
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



@Composable
fun ActivityBlock(activity: Activity, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            //.height((activity.durationMinutes() * 60 / 60).dp)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(activity.color, shape = MaterialTheme.shapes.small)
            .zIndex(1f)
    ) {
        Column(Modifier.padding(4.dp)) {
            Text(activity.title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
            Text("${activity.startTime} - ${activity.endTime}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
        }
    }
}

data class Activity(
    val id: Int,
    val title: String,
    val startTime: String, // "HH:mm" 형식
    val endTime: String,   // "HH:mm" 형식
    val color: Color
) {
    val startHour: Int
        get() = startTime.split(":")[0].toInt()

    fun durationMinutes(): Int {
        val start = startTime.toMinutes()
        val end = endTime.toMinutes()
        return end - start
    }
}

fun String.toMinutes(): Int {
    val parts = this.split(":").map { it.toInt() }
    return parts[0] * 60 + parts[1] // 전체 분으로 변환
}


fun addDays(date: Date, days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_YEAR, days)
    return calendar.time
}


