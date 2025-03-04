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
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
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
@Composable
fun DailyPlannerApp() {
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
            CheckYourLifeAppBar()
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
                TimeColumnName(name="Plan", Modifier.weight(1f))

                // Reality column header
                TimeColumnName(name="Reality", Modifier.weight(1f))
            }
            TimelineContent(scheduledActivities, actualActivities)
        }
    }
}

@Composable
fun TimelineContent(scheduledActivities: List<Activity>, actualActivities: List<Activity>) {
    val listState = rememberLazyListState()
    val listState2 = rememberLazyListState()
    val listState3 = rememberLazyListState()

    // 📌 스크롤 동기화 로직
    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
//        syncScroll(listState, listState2)
    listState2.scrollToItem(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
    listState3.scrollToItem(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
    }

    LaunchedEffect(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset) {
//        syncScroll(listState2, listState)
        listState.scrollToItem(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset)
        listState3.scrollToItem(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset)
    }

    LaunchedEffect(listState3.firstVisibleItemIndex, listState3.firstVisibleItemScrollOffset) {
//        syncScroll(listState2, listState)
        listState.scrollToItem(listState3.firstVisibleItemIndex, listState3.firstVisibleItemScrollOffset)
        listState2.scrollToItem(listState3.firstVisibleItemIndex, listState3.firstVisibleItemScrollOffset)
    }

    Row(Modifier.fillMaxSize()) {
        LazyColumn (
            state = listState
        ) {
            items((0..23).toList()) { hour ->
                TimelineHour(hour)
            }
        }
        LazyColumn(
            state = listState2,
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            items((0..23).toList()) { hour ->
                //TimelineRow(hour, scheduledActivities, actualActivities)
                TimelinePlan(hour, scheduledActivities)
            }
        }
        LazyColumn(
            state = listState3,
            modifier = Modifier.fillMaxWidth()
        ) {
            items((0..23).toList()) { hour ->
                //TimelineRow(hour, scheduledActivities, actualActivities)
                TimelinePlan(hour, actualActivities)
            }
        }
    }
}

@Composable
fun TimelineHour(hour: Int) {
    Row(Modifier.height(60.dp)) {
        // 🕒 시간 표시 영역
        Box(Modifier.width(48.dp)) {
            Text(
                text = String.format("%02d:00", hour),
                Modifier.align(Alignment.CenterStart),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TimelinePlan(hour: Int, scheduledActivities: List<Activity>) {
    Box(Modifier.fillMaxSize()) {
        val activitiesForHour = scheduledActivities.filter { it.startHour == hour }
        val activitiesForMiddleHour = scheduledActivities.filter {it.startHour < hour && ((it.endHour + it.endMiniute.toFloat()/60) > hour)}

        if (activitiesForHour.isEmpty() && activitiesForMiddleHour.isEmpty()) {
            // 🔹 활동이 없을 경우 빈 박스를 추가
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), // 원하는 기본 높이 지정
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Activity", color = Color.Gray, fontSize = 12.sp)
            }
        } else if (activitiesForHour.isNotEmpty()) {
            Divider()
            activitiesForHour.forEach { activity ->
                val (offset, height) = calculateActivityOffsetAndHeight(activity)
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth().height((activity.startMinute).mod(60).dp)
                    ) { Text("Upper Remainder", fontSize = 12.sp) }
                    ActivityBlock(
                        activity,
                        Modifier
                            .fillMaxWidth()
                            .height(height)  // 활동의 길이 설정
                        //.offset(y = offset) // 시작 위치 조정
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth().height((60-activity.endMiniute).mod(60).dp)
                    ) { Text("Lower Remainder", fontSize = 12.sp) }
                }

            }
        }
    }
}

@Composable
fun TimelineRow(hour: Int, scheduledActivities: List<Activity>, actualActivities: List<Activity>) {
    Row() {
        // 🗓️ 일정 (계획된 활동)
        Box(Modifier.weight(1f)) {
            Divider(Modifier.align(Alignment.TopCenter).zIndex(-1f))
            Box(Modifier.fillMaxSize()) {
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
            Divider(Modifier.align(Alignment.TopCenter))
            Box(Modifier.fillMaxSize()) {
                actualActivities.filter { it.startHour == hour }.forEach { activity ->
                    val (offset, height) = calculateActivityOffsetAndHeight(activity)
                    Log.i("height: ", height.toString())
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
    }

}

//@Composable
//fun TimelineContent(
//    modifier: Modifier = Modifier,
//    scheduledActivities: List<Activity>,
//    actualActivities: List<Activity>
//) {
//    val hourHeight = 60.dp
//    val scrollState = rememberScrollState()
//
//    Box(modifier = modifier.fillMaxSize()) {
//        // Background grid layer
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//        ) {
//
//            // Time grid
//            repeat(24) { hour ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(hourHeight)
//                ) {
//                    // Time label
//                    Box(
//                        modifier = Modifier.width(48.dp),
//                        contentAlignment = Alignment.TopStart
//                    ) {
//                        Text(
//                            text = String.format("%02d:00", hour),
//                            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
//                            color = Color.Gray,
//                            fontSize = 12.sp
//                        )
//                    }
//
//                    // Grid columns
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        // Horizontal divider
//                        Divider(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 1.dp),
//                            color = Color.LightGray.copy(alpha = 0.5f)
//                        )
//                    }
//                }
//            }
//        }
//
//        // Activity blocks overlay layer
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(scrollState) // Use the same scroll state
//        ) {
//            // Scheduled activities (Plan column)
//            scheduledActivities.forEach { activity ->
//                val startOffsetY = hourHeight * (activity.startHour + (activity.startMinute / 60f))
//                val duration = hourHeight * (activity.durationMinutes() / 60f)
//
//                Box(
//                    modifier = Modifier
//                        .padding(start = 48.dp, end = 4.dp)
//                        .width(with(LocalDensity.current) {
//                            (LocalConfiguration.current.screenWidthDp / 2 - 24).dp
//                        })
//                        .height(duration*2)
//                        .offset(y = startOffsetY)
//                ) {
//                    ActivityBlock(activity)
//                }
//            }
//        }
//    }
//}



//@Composable
//fun TimelineRow(hour: Int) {
//    val hourRowHeight = 60.dp // 시간 행 높이
//
//    Row(Modifier.height(hourRowHeight)) {
//        // 🕒 시간 표시 영역
//        Box(Modifier.width(48.dp)) {
//            Text(
//                text = String.format("%02d:00", hour),
//                Modifier.align(Alignment.CenterStart),
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
//        }
//
//        // 🗓️ 그리드 라인
//        Box(Modifier.weight(1f)) {
//            Divider(Modifier.align(Alignment.TopCenter)) // 60.dp 마다 Divider 추가
//        }
//
//        Box(Modifier.weight(1f)) {
//            Divider(Modifier.align(Alignment.TopCenter)) // Reality 컬럼도 동일하게 Divider 추가
//        }
//    }
//}


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

    val endHour: Int
        get() = endTime.split(":")[0].toInt()

    val startMinute: Int
        get() = startTime.split(":")[1].toInt()

    val endMiniute: Int
        get() = endTime.split(":")[1].toInt()


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


