package com.rali.timelane.presentation.routineButton

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RoutineFloatingButton() {
    var isExpanded by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.End) {
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(20.dp)
            ) {
                FabItem(
                    title = "오늘을 루틴으로 등록하기",
                    icon = Icons.Filled.Add,
                    onClicked = { showConfirmDialog = true }
                )
                Spacer(modifier = Modifier.height(20.dp))
                FabItem(
                    title = "루틴 넣기",
                    icon = Icons.AutoMirrored.Filled.List,
                    onClicked = { /* ... */ }
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

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("확인") },
            text = { Text("오늘을 루틴으로 등록하시겠습니까?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        isExpanded = false
                    }
                ) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        isExpanded = false
                    }
                ) {
                    Text("취소")
                }
            }
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