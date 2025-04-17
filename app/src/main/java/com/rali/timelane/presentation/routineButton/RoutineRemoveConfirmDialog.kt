package com.rali.timelane.presentation.routineButton

import androidx.compose.runtime.Composable
import com.rali.timelane.presentation.common.CustomAlertDialog

@Composable
fun RoutineRemoveConfirmDialog(
    routineName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomAlertDialog(
        dialogTitle = "루틴 삭제",
        dialogText = "'${routineName}' 루틴을 삭제하시겠습니까?",
        onConfirm,
        onDismiss
    )
}