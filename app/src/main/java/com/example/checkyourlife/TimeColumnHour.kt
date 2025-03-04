package com.example.checkyourlife

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeColumnHour(hour: Int) {
    Row(Modifier.height(60.dp)) {
        // 🕒 시간 표시 영역
        Box(Modifier.width(48.dp)) {
            Text(
                text = String.format("%02d:00", hour),
                Modifier.align(Alignment.CenterStart),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}