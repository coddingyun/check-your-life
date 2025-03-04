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
                TimeColumnHour(hour)
            }
        }
        LazyColumn(
            state = listState2,
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            items((0..23).toList()) { hour ->
                //TimelineRow(hour, scheduledActivities, actualActivities)
                TimeColumn(hour, scheduledActivities)
            }
        }
        LazyColumn(
            state = listState3,
            modifier = Modifier.fillMaxWidth()
        ) {
            items((0..23).toList()) { hour ->
                //TimelineRow(hour, scheduledActivities, actualActivities)
                TimeColumn(hour, actualActivities)
            }
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


