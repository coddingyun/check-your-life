package com.rali.timelane

class BlockDialogValidator {
    fun validate(
        title: String?,
        startHour: Int?,
        startMinute: Int?,
        endHour: Int?,
        endMinute: Int?,
        activities: List<Activity>,
        currentDate: Long
    ): ValidationResult {
        if (title.isNullOrBlank() || startHour == null || startMinute == null || endHour == null || endMinute == null) {
            return ValidationResult.Invalid("입력하지 않은 값이 있는 지 확인해주세요.")
        }

        val newStart = startHour * 60 + startMinute
        val newEnd = endHour * 60 + endMinute

        if (newStart >= newEnd) {
            return ValidationResult.Invalid("시작 시간이 끝 시간보다 작아야 합니다.")
        }

        val isOverlapping = activities
            .filter { it.date == currentDate }
            .any { activity ->
                val activityStart = activity.startHour * 60 + activity.startMinute
                val activityEnd = activity.endHour * 60 + activity.endMiniute
                (newStart < activityEnd && newEnd > activityStart) // 시간이 겹치는 경우
            }

        if (isOverlapping) {
            return ValidationResult.Invalid("해당 시간대에 이미 활동이 존재합니다.")
        }

        return ValidationResult.Valid
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()
}
