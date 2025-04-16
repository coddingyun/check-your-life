package com.rali.timelane.presentation.routineButton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rali.timelane.domain.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: RoutineRepository
): ViewModel() {
    private val _routines = MutableStateFlow<List<Routine>>(emptyList())
    val routines = _routines.asStateFlow()

    fun addRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRoutine(routine)
    }

    fun getAllRoutines() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllRoutines().collect { routine ->
            _routines.value = routine
        }
    }

    fun removeRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRoutine(routine)
    }

    fun removeAllRoutines() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllRoutines()
    }


}