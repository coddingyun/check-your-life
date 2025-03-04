package com.example.checkyourlife

import androidx.compose.ui.graphics.Color

data class Activity(
    val id: Int,
    val title: String,
    val startTime: String, // "HH:mm" 형식
    val endTime: String,   // "HH:mm" 형식
    val color: Color
) {
    val startHour: Int
        get() = startTime.split(":")[0].toInt()

    val endHour: Int
        get() = endTime.split(":")[0].toInt()

    val startMinute: Int
        get() = startTime.split(":")[1].toInt()

    val endMiniute: Int
        get() = endTime.split(":")[1].toInt()


    fun durationMinutes(): Int {
        val start = startTime.toMinutes()
        val end = endTime.toMinutes()
        return end - start
    }
}

fun String.toMinutes(): Int {
    val parts = this.split(":").map { it.toInt() }
    return parts[0] * 60 + parts[1] // 전체 분으로 변환
}