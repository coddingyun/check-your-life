package com.rali.timelane.presentation.routineButton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.checkyourlife.Activity
import com.rali.timelane.presentation.common.DialogButton
import com.rali.timelane.presentation.common.DialogCard
import com.rali.timelane.presentation.common.DialogTextField

@Composable
fun MakeRoutineDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    plannedActivities: List<Activity>,
    routineViewModel: RoutineViewModel = hiltViewModel(),
) {
    var routineTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        DialogCard(
            title = "루틴 만들기",
            onDismiss = onDismiss,
        ) {
            DialogTextField(
                value = routineTitle,
                onValueChange = { newValue ->
                    routineTitle = newValue
                },
                label = "루틴 이름"
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DialogButton(
                    text = "확인",
                    onClick = {
                        routineViewModel.addRoutine(
                            Routine(
                                title = routineTitle,
                                activities = plannedActivities
                            )
                        )
                        onConfirm()
                    }
                )

            }
        }
    }
}