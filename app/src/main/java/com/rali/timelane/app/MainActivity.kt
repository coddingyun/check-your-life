package com.rali.checkyourlife

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rali.checkyourlife.ui.theme.CheckYourLifeTheme
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.MobileAds
import com.rali.timelane.presentation.activityBlock.ActivityViewModel
import com.rali.timelane.presentation.timeColumn.TimeColumn
import com.rali.timelane.presentation.timeColumn.TimeColumnHour
import com.rali.timelane.presentation.timeColumn.TimeColumnName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val dayTimePickerViewModelForStartTime: DayTimePickerViewModelForStartTime by viewModels()
    //private val dayTimePickerViewModelForEndTime: DayTimePickerViewModelForEndTime by viewModels()
    //private val makeBlockDialogViewModel: MakeBlockDialogViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
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
fun DailyPlannerApp(
    mainViewModel: MainViewModel = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
    activityViewModel: ActivityViewModel = hiltViewModel(),
) {
    val dialogState = makeBlockDialogViewModel.blockDialogState.value
    val mainState = mainViewModel.mainState.value

    if (dialogState?.isShowMakeBlockDialog == true) {
        MakeBlockDialog(
            onConfirm = { title, color, startTime, endTime, activityType ->
                activityViewModel.addActivity(
                    Activity(
                        title = title,
                        date = mainState?.date!!,
                        colorInt = color.toArgb(),
                        startTime = startTime,
                        endTime = endTime,
                        type = activityType.name,
                    )
                )
                dialogState.onConfirm(title, color, startTime, endTime, activityType)
            },
            onRemove = {},
            onDismiss = {
                dialogState.onDismiss()
            },
        )
    } else if (dialogState?.isShowUpdateBlockDialog == true) {
        MakeBlockDialog(
            onConfirm = { title, color, startTime, endTime, activityType ->
                activityViewModel.updateActivity(
                    Activity(
                        id = dialogState.id!!,
                        title = title,
                        date = mainState?.date!!,
                        colorInt = color.toArgb(),
                        startTime = startTime,
                        endTime = endTime,
                        type = activityType.name,
                    )
                )
                dialogState.onConfirm(title, color, startTime, endTime, activityType)
            },
            onRemove = {
                activityViewModel.removeActivity(
                    Activity(
                        id = dialogState.id!!,
                        title = dialogState.title,
                        date = mainState?.date!!,
                        colorInt = dialogState.color.toArgb(),
                        startTime = formatHHmm(dialogState.startHour!!, dialogState.startMinute!!),
                        endTime = formatHHmm(dialogState.endHour!!, dialogState.endMinute!!),
                        type = dialogState.activityType!!.name,
                    )
                )
                makeBlockDialogViewModel.closeUpdateBlockDialog()
            },
            onDismiss = {
                dialogState.onDismiss()
                makeBlockDialogViewModel.closeUpdateBlockDialog()
            },
        )
    }

    Scaffold(
        topBar = {
            CheckYourLifeAppBar()
        },
        bottomBar = {
            BannersAds(
                modifier = Modifier
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()) // ✅ 네비게이션 바 높이만큼 패딩 추가
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
                TimeColumnName(name="Plan", Modifier.weight(1f))

                // Reality column header
                TimeColumnName(name="Reality", Modifier.weight(1f))
            }
            TimelineContent()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineContent(
    activityViewModel: ActivityViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val mainState = mainViewModel.mainState.value
    val plannedActivities by activityViewModel.plannedActivities.collectAsState()
    val actualActivities by activityViewModel.actualActivities.collectAsState()
    val scrollState = rememberScrollState()

    Row(
        Modifier.verticalScroll(scrollState)
    ) {
        Column {
            (0..23).forEach { hour ->
                TimeColumnHour(hour)
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            DividerLayer()
            Column(modifier = Modifier.fillMaxSize()) {
                (0..23).forEach { hour ->
                    TimeColumn(hour, plannedActivities.filter { it.date == mainState?.date!! })
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            DividerLayer()
            Column(modifier = Modifier.fillMaxSize()) {
                (0..23).forEach { hour ->
                    TimeColumn(hour, actualActivities.filter { it.date == mainState?.date!! })
                }
            }
        }
    }
}
