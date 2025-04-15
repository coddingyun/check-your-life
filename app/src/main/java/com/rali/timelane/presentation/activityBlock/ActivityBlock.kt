package com.rali.checkyourlife

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityBlock(
    activity: Activity,
    modifier: Modifier = Modifier,
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
) {
    Box(
        modifier = modifier
            .clickable {
                makeBlockDialogViewModel.putActivityInfo(activity)
            } // 클릭 시 다이얼로그 표시
            .padding(horizontal = 4.dp)
            .background(activity.color, shape = MaterialTheme.shapes.small)
            .zIndex(1f)
    ) {
        Column(Modifier.padding(4.dp)) {
            Text(activity.title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
            Text("${activity.startTime} - ${activity.endTime}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
        }
    }
}