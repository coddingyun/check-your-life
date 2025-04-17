package com.rali.checkyourlife

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId

fun formatHHmm(hour: Int, minute: Int): String {
    val time = String.format("%02d:%02d", hour, minute)
    return time
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTodayMilliSeconds(): Long {
    return LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}