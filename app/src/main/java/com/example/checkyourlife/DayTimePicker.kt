package com.example.checkyourlife

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayTimePicker(
    timePickerState: TimePickerState
) {
    val currentTime = Calendar.getInstance()

    TimePicker(
        state = timePickerState,
    )
}
