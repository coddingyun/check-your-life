package com.example.checkyourlife

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: 맞게 바꾸어야 함
data class BlockDialogState(
    var title: String = "",
    var color: Color? = null,
    var startTime: Int? = null,
    var endTime: Int? = null,
    var isShowBlockDialog: Boolean = false,
    val onConfirm: (title: String, color: Color, startTime: Int, endTime: Int) -> Unit,
    val onDismiss: () -> Unit,
)

@HiltViewModel
class MakeBlockDialogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle?,
) : ViewModel() {
    val blockDialogState: MutableState<BlockDialogState?>
        = mutableStateOf(null)

    init {
        blockDialogState.value = BlockDialogState(
            onConfirm = { title, color, startTime, endTime ->
                blockDialogState.value = blockDialogState.value?.copy(
                    isShowBlockDialog = false,
                    title = title,
                    color = color,
                )
            },
            onDismiss = {
                blockDialogState.value = blockDialogState.value?.copy(
                    isShowBlockDialog = false
                )
            }
        )
    }

    fun showBlockDialog() {
        blockDialogState.value =
            blockDialogState.value?.copy(isShowBlockDialog = true)
    }
}
