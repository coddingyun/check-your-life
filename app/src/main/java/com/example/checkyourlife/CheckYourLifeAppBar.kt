package com.example.checkyourlife

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckYourLifeAppBar() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    TopAppBar(
        title = {
        },
        actions = {
            IconButton(onClick = { currentDate = currentDate.minusDays(1) }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Day")
            }

            // 가운데 위치하도록 하기 위해 Modifier.weight(1f)를 사용
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),  // Box가 높이를 채우도록 함
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sat, Mar 1",
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(onClick = { currentDate = currentDate.plusDays(1) }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Day")
            }
        }
    )
}