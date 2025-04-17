package com.rali.timelane.presentation.routineButton

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.checkyourlife.HomeViewModel
import com.rali.timelane.presentation.activityBlock.ActivityViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineItem(
    routine: Routine,
    onConfirm: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    activityViewModel: ActivityViewModel = hiltViewModel()
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showConfirmDialog = true
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = routine.title)

            IconButton(
                onClick = {
                    // TODO: 삭제 동작
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }

    if (showConfirmDialog) {
        RoutineConfirmDialog(
            routineName = routine.title,
            onConfirm = {
                val date = homeViewModel.homeState.value!!.date

                val updatedActivities = routine.activities.map { activity ->
                    activity.copy(date = date)
                }
                activityViewModel.updateRoutine(date, updatedActivities)

                showConfirmDialog = false
                onConfirm()
            },
            onDismiss = {
                showConfirmDialog = false
            }
        )
    }
}