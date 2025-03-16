package com.rali.timelane

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ActivityBlock(
    activity: Activity,
    modifier: Modifier = Modifier,
    activityViewModel: ActivityViewModel = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
) {
    val dialogState = makeBlockDialogViewModel.blockDialogState.value

    Box(
        modifier = modifier
            .clickable {
                makeBlockDialogViewModel.putActivityInfo(activity)
                makeBlockDialogViewModel.showUpdateBlockDialog(
                    if (activity.type == "PLAN") ActivityType.PLAN else ActivityType.REALITY
                )
            } // 클릭 시 다이얼로그 표시
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(activity.color, shape = MaterialTheme.shapes.small)
            .zIndex(1f)
    ) {
        Column(Modifier.padding(4.dp)) {
            Text(activity.title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
            Text("${activity.startTime} - ${activity.endTime}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
        }
    }

    if (dialogState?.isShowUpdateBlockDialog == true) {
        // 이 안에서는 모든 activity가 들어온다 -> clickable에서 통제해야 함.
        MakeBlockDialog(
            onConfirm = { title, color, startTime, endTime, activityType ->
                activityViewModel.updateActivity(
                    activity.copy(
                        id = dialogState.id!!,
                        title = title,
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
                    activity.copy(
                        id = dialogState.id!!,
                    )
                )
                makeBlockDialogViewModel.closeUpdateBlockDialog()
            },
            onDismiss = {
                dialogState.onDismiss()
            },
        )
    }
}