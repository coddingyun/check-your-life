package com.rali.timelane

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

    val formatToTime: String?
        get() {
            return if (selectedHour != null && selectedMinute != null) {
                val time: String = String.format("%02d:%02d", selectedHour, selectedMinute)
                time
            } else {
                null
            }
        }
}