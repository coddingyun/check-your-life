package com.rali.timelane.presentation.routineButton

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.timelane.presentation.activityBlock.ActivityViewModel
import com.rali.timelane.presentation.common.CustomAlertDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineFloatingButton(
    routineViewModel: RoutineViewModel = hiltViewModel(),
    activityViewModel: ActivityViewModel = hiltViewModel(),
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showMakeRoutineDialog by remember { mutableStateOf(false) }
    var showRoutineListDialog by remember { mutableStateOf(false) }
    val plannedActivities = activityViewModel.plannedActivities.collectAsState()

    Column(horizontalAlignment = Alignment.End) {
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(20.dp)
            ) {
                FabItem(
                    title = "오늘 Plan을 루틴으로 등록하기",
                    icon = Icons.Filled.Add,
                    onClicked = { showMakeRoutineDialog = true }
                )
                Spacer(modifier = Modifier.height(20.dp))
                FabItem(
                    title = "오늘 Plan에 루틴 넣기",
                    icon = Icons.AutoMirrored.Filled.List,
                    onClicked = { showRoutineListDialog = true }
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        FloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            shape = CircleShape,
            containerColor = if (isExpanded) Color.White else Color.Gray
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Add,
                tint = if (isExpanded) Color.Gray else Color.White,
                contentDescription = "This is Expand Button"
            )
        }
    }

    if (showMakeRoutineDialog) {
        MakeRoutineDialog(
            plannedActivities = plannedActivities.value,
            onConfirm = {
                showMakeRoutineDialog = false
                isExpanded = false
            },
            onDismiss = {
                showMakeRoutineDialog = false
                isExpanded = false
            }
        )
    }

    if (showRoutineListDialog) {
        RoutineListDialog(
            onConfirm = {
                showRoutineListDialog = false
                isExpanded = false
            },
            onDismiss = {
                showRoutineListDialog = false
                isExpanded = false
            },
        )
    }
}

@Composable
fun FabItem(title: String, icon: ImageVector, onClicked: () -> Unit) {
    Row(modifier = Modifier.clickable {
        onClicked()
    }, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "FabItem Icon",
            tint = Color.DarkGray
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            color = Color.DarkGray
        )
    }
}