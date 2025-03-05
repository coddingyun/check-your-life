package com.example.checkyourlife

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class DayTimePickerState(
    var selectedHour: Int? = null,
    var selectedMinute: Int? = null,
    var isShowTimePicker: Boolean = false,
    val onConfirm: (hour: Int, minute: Int) -> Unit,
    val onDismiss: () -> Unit,
) {
    val selectedHHmm: String?
        get() {
            return if (selectedHour != null && selectedMinute != null) {
                val time = String.format("%02d%02d", selectedHour, selectedMinute)
                time
            } else {
                null
            }
        }
}

@HiltViewModel
class DayTimePickerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle?,
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
