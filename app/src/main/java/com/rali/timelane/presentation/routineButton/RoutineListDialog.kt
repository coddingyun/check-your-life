package com.rali.timelane.presentation.routineButton

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.timelane.presentation.common.DialogCard

@Composable
fun RoutineListDialog(
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
                RoutineItem(routine = it)
            }
        }
    }
}

@Composable
fun RoutineItem(routine: Routine) {
    Row {
        Text(text = routine.title)
    }
}