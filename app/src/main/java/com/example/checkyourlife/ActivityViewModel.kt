package com.example.checkyourlife

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val repository: ActivityRepository
) : ViewModel() {
    private val _planndedActivities = MutableStateFlow<List<Activity>>(emptyList())
    val plannedActivities = _planndedActivities.asStateFlow()

    private val _actualActivities = MutableStateFlow<List<Activity>>(emptyList())
    val actualActivities = _actualActivities.asStateFlow()

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlannedActivities().distinctUntilChanged().collect { plannedActivity ->
                _planndedActivities.value = plannedActivity
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getActualActivities().distinctUntilChanged().collect { actualActivity ->
                _actualActivities.value = actualActivity
            }
        }
    }

    fun addActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertActivity(activity)
    }

    fun removeAllActivities() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllActivites()
    }
}
