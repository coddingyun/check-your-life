package com.rali.checkyourlife

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val date: Long = 0, // milliseconds
    val startTime: String, // "HH:mm" 형식
    val endTime: String,   // "HH:mm" 형식
    val colorInt: Int,
    val type: String // "PLAN" 또는 "REALITY" 구분
) {
    val color: Color
        get() = Color(colorInt)

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