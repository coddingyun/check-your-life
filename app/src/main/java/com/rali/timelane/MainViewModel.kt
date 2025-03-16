package com.rali.timelane

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

data class MainState(
    var date: Long = 0,
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle?,
): ViewModel() {
    val mainState: MutableState<MainState?>
        = mutableStateOf(null)

    init {
        mainState.value = MainState(
            date = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    fun formateDate(): String {
        val currentDateTime =
            Instant.ofEpochMilli(mainState.value?.date!!).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(currentDateTime)
    }

    fun minusDay1() {
        val currentDate = mainState.value?.date ?: return

        val newDate = Instant.ofEpochMilli(currentDate)
            .minus(1, ChronoUnit.DAYS) // ✅ 정확하게 하루만 감소
            .toEpochMilli()

        mainState.value = mainState.value?.copy(date = newDate)
    }

    fun plusDay1() {
        val currentDate = mainState.value?.date ?: return

        val newDate = Instant.ofEpochMilli(currentDate)
            .plus(1, ChronoUnit.DAYS) // ✅ 정확하게 하루만 증가
            .toEpochMilli()

        mainState.value = mainState.value?.copy(date = newDate)
    }
}