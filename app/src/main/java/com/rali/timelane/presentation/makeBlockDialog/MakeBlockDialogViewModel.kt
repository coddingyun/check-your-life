package com.rali.checkyourlife

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.rali.timelane.presentation.activityBlock.ActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// TODO: 맞게 바꾸어야 함
data class BlockDialogState(
    var id: Long? = null, // Activity id 임시 저장소 (update할 때를 위한)
    var title: String = "",
    var color: Color = Color(0xFFFF0000),
    var startHour: Int? = null,
    var startMinute: Int? = null,
    var endHour: Int? = null,
    var endMinute: Int? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var isShowMakeBlockDialog: Boolean = false,
    var isShowUpdateBlockDialog: Boolean = false,
    var activityType: ActivityType? = null,
    val onConfirm: (title: String, color: Color, startTime: String, endTime: String, activityType: ActivityType) -> Unit,
    val onDismiss: () -> Unit,
)

@HiltViewModel
class MakeBlockDialogViewModel @Inject constructor(
    private val blockDialogValidator: BlockDialogValidator,
) : ViewModel() {
    private val _validationState = MutableStateFlow<ValidationResult?>(null)
    val validationState: StateFlow<ValidationResult?> = _validationState

    fun validateBlock(
        id: Long?,
        title: String?,
        startHour: Int?,
        startMinute: Int?,
        endHour: Int?,
        endMinute: Int?,
        activities: List<Activity>,
        currentDate: Long,
        isCopy: Boolean,
    ) {
        Log.i("activities", "$startHour, $startMinute, $endHour, $endMinute, $activities")
        _validationState.value = blockDialogValidator.validate(id, title, startHour, startMinute, endHour, endMinute, activities, currentDate, isCopy)
    }

    fun initValidationState() {
        _validationState.value = null
    }

    fun initBlockDialogState() {
        blockDialogState.value = BlockDialogState(
            id = null,
            title = "",
            color = Color(0xFFFF0000),
            startHour = null,
            startMinute = null,
            endHour = null,
            endMinute = null,
            onConfirm = { title, color, startTime, endTime, activityType ->
                blockDialogState.value = blockDialogState.value?.copy(
                    id = null,
                    title = "",
                    color = Color(0xFFFF0000),
                    startHour = null,
                    startMinute = null,
                    endHour = null,
                    endMinute = null,
                    isShowMakeBlockDialog = false,
                    isShowUpdateBlockDialog = false,
                )
                initValidationState()
            },
            onDismiss = {
                blockDialogState.value = blockDialogState.value?.copy(
                    id = null,
                    title = "",
                    color = Color(0xFFFF0000),
                    startHour = null,
                    startMinute = null,
                    endHour = null,
                    endMinute = null,
                    isShowMakeBlockDialog = false,
                    isShowUpdateBlockDialog = false,
                )
                initValidationState()
            }
        )
    }

    val blockDialogState: MutableState<BlockDialogState?>
        = mutableStateOf(null)

    init {
        initBlockDialogState()
    }

    fun setStartTime(hour: Int, minute: Int) {
        blockDialogState.value = blockDialogState.value?.copy(
            startHour = hour,
            startMinute = minute
        )
    }

    fun setEndTime(hour: Int, minute: Int) {
        blockDialogState.value = blockDialogState.value?.copy(
            endHour = hour,
            endMinute = minute
        )
    }

    fun setColor(color: Color) {
        blockDialogState.value = blockDialogState.value?.copy(
            color = color,
        )
    }

    fun putActivityInfo(activity: Activity) {
        blockDialogState.value = blockDialogState.value?.copy(
            id = activity.id,
            title = activity.title,
            startHour = activity.startHour,
            startMinute = activity.startMinute,
            endHour = activity.endHour,
            endMinute = activity.endMiniute,
            color = activity.color,
            isShowUpdateBlockDialog = true, // updateBlockDialog 열기
            activityType = if (activity.type == "PLAN") ActivityType.PLAN else ActivityType.REALITY
        )
    }

    fun updateTitle(newTitle: String) {
        blockDialogState.value = blockDialogState.value?.copy(
            title = newTitle,
        )
    }

    fun showMakeBlockDialog(activityType: ActivityType) {
        blockDialogState.value =
            blockDialogState.value?.copy(
                isShowMakeBlockDialog = true,
                activityType = activityType,
            )
    }

    fun showUpdateBlockDialog(activityType: ActivityType) {
        blockDialogState.value =
            blockDialogState.value?.copy(
                isShowUpdateBlockDialog = true,
                activityType = activityType,
            )
    }

    fun closeUpdateBlockDialog() {
        blockDialogState.value =
            blockDialogState.value?.copy(
                id = null,
                title = "",
                startHour = null,
                startMinute = null,
                endHour = null,
                endMinute = null,
                color = Color(0xFFFF0000),
                activityType = null,
                isShowUpdateBlockDialog = false,
            )
    }
}
