package com.example.checkyourlife

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val repository: ActivityRepository
) : ViewModel() {

    val plannedActivities: LiveData<List<Activity>> = liveData {
        emit(repository.getPlannedActivities())
    }

    val actualActivities: LiveData<List<Activity>> = liveData {
        emit(repository.getActualActivities())
    }

    fun addActivity(activity: Activity) = viewModelScope.launch {
        repository.insertActivity(activity)
    }

    fun removeActivity(activity: Activity) = viewModelScope.launch {
        repository.deleteActivity(activity)
    }
}
