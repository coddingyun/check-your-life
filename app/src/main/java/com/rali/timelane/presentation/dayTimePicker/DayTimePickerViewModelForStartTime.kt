package com.rali.timelane.presentation.dayTimePicker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayTimePickerViewModelForStartTime @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val timePickerState: MutableState<DayTimePickerState?> =
        mutableStateOf(null)

    init {
        timePickerState.value = DayTimePickerState(
            onConfirm = { hour, minute ->
                timePickerState.value = timePickerState.value?.copy(
                    isShowTimePicker = false,
                    selectedHour = hour,
                    selectedMinute = minute
                )
            },
            onDismiss = {
                timePickerState.value = timePickerState.value?.copy(
                    isShowTimePicker = false
                )
            }
        )
    }

    fun showTimePickerDialog() {
        timePickerState.value =
            timePickerState.value?.copy(isShowTimePicker = true)
    }
}
