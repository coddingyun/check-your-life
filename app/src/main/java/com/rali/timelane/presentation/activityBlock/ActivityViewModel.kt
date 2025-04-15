package com.rali.timelane.presentation.activityBlock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rali.checkyourlife.Activity
import com.rali.timelane.domain.repository.ActivityRepository
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
    private val _plannedActivities = MutableStateFlow<List<Activity>>(emptyList())
    val plannedActivities = _plannedActivities.asStateFlow()

    private val _actualActivities = MutableStateFlow<List<Activity>>(emptyList())
    val actualActivities = _actualActivities.asStateFlow()

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlannedActivities().distinctUntilChanged().collect { plannedActivity ->
                _plannedActivities.value = plannedActivity
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

    fun updateActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateActivity(activity)
    }

    fun removeActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteActivity(activity)
    }

    fun removeAllActivities() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllActivites()
    }
}
