package com.example.checkyourlife

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ColorPickerState (
    var color: Color? = null,
    var isShowColorPicker: Boolean = false,
    val onConfirm: (color: Color) -> Unit,
    val onDismiss: () -> Unit
)

@HiltViewModel
class ColorPickerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle?,
) : ViewModel() {
    val colorPickerState: MutableState<ColorPickerState?>
            = mutableStateOf(null)

    init {
        colorPickerState.value = ColorPickerState(
            onConfirm = { color ->
                colorPickerState.value = colorPickerState.value?.copy(
                    isShowColorPicker = false,
                    color = color,
                )
            },
            onDismiss = {
                colorPickerState.value = colorPickerState.value?.copy(
                    isShowColorPicker = false
                )
            }
        )
    }

    fun showColorPickerDialog() {
        colorPickerState.value =
            colorPickerState.value?.copy(isShowColorPicker = true)
    }
}