package com.rali.timelane.presentation.routineButton

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.timelane.presentation.common.DialogCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineListDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    routineViewModel: RoutineViewModel = hiltViewModel(),
) {
    val routines = routineViewModel.routines.collectAsState()

    LaunchedEffect(Unit) {
        routineViewModel.getAllRoutines()
    }

    Dialog(
        onDismissRequest = {}
    ) {
        DialogCard (
            title = "루틴 목록",
            onDismiss = onDismiss
        ){
            routines.value.forEach {
                RoutineItem(
                    routine = it,
                    onConfirm,
                )
            }
        }
    }
}
