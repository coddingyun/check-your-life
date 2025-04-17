package com.rali.timelane.presentation.routineButton

import androidx.compose.runtime.Composable
import com.rali.timelane.presentation.common.CustomAlertDialog

@Composable
fun RoutineConfirmDialog(
    routineName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomAlertDialog(
        dialogTitle = "루틴 추가",
        dialogText = "${routineName} 루틴을 오늘 Plan에 넣으시겠습니까?\n" +
                "현재 존재하는 Plan은 모두 사라집니다.",
        onConfirm,
        onDismiss
    )
}