package com.example.checkyourlife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ActivityBlock(activity: Activity, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            //.height((activity.durationMinutes() * 60 / 60).dp)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(activity.color, shape = MaterialTheme.shapes.small)
            .zIndex(1f)
    ) {
        Column(Modifier.padding(4.dp)) {
            Text(activity.title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
            Text("${activity.startTime} - ${activity.endTime}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
        }
    }
}