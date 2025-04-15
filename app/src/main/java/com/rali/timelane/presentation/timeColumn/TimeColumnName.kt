package com.rali.timelane.presentation.timeColumn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.timelane.presentation.activityBlock.ActivityType
import com.rali.checkyourlife.MakeBlockDialogViewModel

@Composable
fun TimeColumnName(name: String,
                   modifier: Modifier,
                   makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier
            //.weight(1f)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            IconButton(onClick = {
                makeBlockDialogViewModel.showMakeBlockDialog(
                    if (name == "Plan") ActivityType.PLAN else ActivityType.REALITY
                )
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Gray
                )
            }
        }
    }
}