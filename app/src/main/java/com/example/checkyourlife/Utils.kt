package com.example.checkyourlife

fun formatHHmm(hour: Int, minute: Int): String {
    val time = String.format("%02d:%02d", hour, minute)
    return time
}