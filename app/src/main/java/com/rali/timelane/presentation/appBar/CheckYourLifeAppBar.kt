package com.rali.checkyourlife

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckYourLifeAppBar(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val homeState = homeViewModel.homeState.value
    val koreaZoneId = ZoneId.of("Asia/Seoul")

    TopAppBar(
        title = {
        },
        actions = {
            IconButton(onClick = { homeViewModel.minusDay1() }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Day")
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { showDatePicker = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = homeViewModel.formateDate(),
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(onClick = { homeViewModel.plusDay1() }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Day")
            }
        }
    )
    
    if (showDatePicker) {
        val initialUtcMillis = homeState?.date?.let { localMillis ->
            Instant.ofEpochMilli(localMillis)
                .atZone(koreaZoneId)
                .toLocalDate()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        } ?: null
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialUtcMillis
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(koreaZoneId)
                                .toLocalDate()
                            val correctedMillis = localDate
                                .atStartOfDay(koreaZoneId)
                                .toInstant()
                                .toEpochMilli()

                            homeViewModel.setDate(correctedMillis)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("취소")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}