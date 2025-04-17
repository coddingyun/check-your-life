package com.rali.checkyourlife

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

data class HomeState(
    val date: Long = 0,
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle?,
): ViewModel() {
    val homeState: MutableState<HomeState?>
        = mutableStateOf(null)

    init {
        homeState.value = HomeState(
            date = getTodayMilliSeconds()
        )
    }

    fun setDate(date: Long) {
        homeState.value = homeState.value?.copy(date = date)
    }

    fun formateDate(): String {
        return Instant.ofEpochMilli(homeState.value?.date!!)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
    }

    fun minusDay1() {
        val currentDate = homeState.value?.date ?: return

        val newDate = Instant.ofEpochMilli(currentDate)
            .minus(1, ChronoUnit.DAYS) // ✅ 정확하게 하루만 감소
            .toEpochMilli()

        homeState.value = homeState.value?.copy(date = newDate)
    }

    fun plusDay1() {
        val currentDate = homeState.value?.date ?: return

        val newDate = Instant.ofEpochMilli(currentDate)
            .plus(1, ChronoUnit.DAYS) // ✅ 정확하게 하루만 증가
            .toEpochMilli()

        homeState.value = homeState.value?.copy(date = newDate)
    }
}