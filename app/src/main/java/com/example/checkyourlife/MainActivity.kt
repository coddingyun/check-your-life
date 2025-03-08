package com.example.checkyourlife

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.checkyourlife.ui.theme.CheckYourLifeTheme
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val dayTimePickerViewModelForStartTime: DayTimePickerViewModelForStartTime by viewModels()
    //private val dayTimePickerViewModelForEndTime: DayTimePickerViewModelForEndTime by viewModels()
    //private val makeBlockDialogViewModel: MakeBlockDialogViewModel by viewModels()

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


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyPlannerApp(
    dayTimePickerViewModelForStartTime: DayTimePickerViewModelForStartTime = hiltViewModel(),
    dayTimePickerViewModelForEndTime: DayTimePickerViewModelForEndTime = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel()
) {
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

    val timePickerStateForStartTime = dayTimePickerViewModelForStartTime.timePickerState.value
    val timePickerStateForEndTime = dayTimePickerViewModelForEndTime.timePickerState.value
    val dialogState = makeBlockDialogViewModel.blockDialogState.value

    if (dialogState?.isShowBlockDialog == true) {
        MakeBlockDialog(
            dayTimePickerViewModelForStartTime,
            dayTimePickerViewModelForEndTime,
            onConfirm = { title, color, startTime, endTime ->
                dialogState.onConfirm(title, color, startTime, endTime)
            },
            onDismiss = {
                dialogState.onDismiss()
            },
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
                TimeColumnName(name="Plan", Modifier.weight(1f), makeBlockDialogViewModel)

                // Reality column header
                TimeColumnName(name="Reality", Modifier.weight(1f), makeBlockDialogViewModel)
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
    listState2.scrollToItem(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
    listState3.scrollToItem(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
    }

    LaunchedEffect(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset) {
        listState.scrollToItem(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset)
        listState3.scrollToItem(listState2.firstVisibleItemIndex, listState2.firstVisibleItemScrollOffset)
    }

    LaunchedEffect(listState3.firstVisibleItemIndex, listState3.firstVisibleItemScrollOffset) {
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
                TimeColumn(hour, scheduledActivities)
            }
        }
        LazyColumn(
            state = listState3,
            modifier = Modifier.fillMaxWidth()
        ) {
            items((0..23).toList()) { hour ->
                TimeColumn(hour, actualActivities)
            }
        }
    }
}
