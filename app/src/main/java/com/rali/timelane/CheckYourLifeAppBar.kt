package com.rali.checkyourlife

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckYourLifeAppBar(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    //var currentDate by remember { mutableStateOf(LocalDate.now()) }
    //val mainState = mainViewModel.mainState.value

    TopAppBar(
        title = {
        },
        actions = {
            IconButton(onClick = { mainViewModel.minusDay1() }) {
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
                    text = mainViewModel.formateDate(),
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(onClick = { mainViewModel.plusDay1() }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Day")
            }
        }
    )
}