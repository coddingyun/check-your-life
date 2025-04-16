package com.rali.timelane.domain.repository

import com.rali.timelane.domain.dao.RoutineDao
import com.rali.timelane.presentation.routineButton.Routine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao: RoutineDao
){
    suspend fun insertRoutine(routine: Routine) {
        routineDao.insertRoutine(routine)
    }

    suspend fun getAllRoutines(): Flow<List<Routine>> {
        return routineDao.getAllRoutines().flowOn(Dispatchers.IO)
    }

    suspend fun deleteRoutine(routine: Routine) {
        routineDao.deleteRoutine(routine)
    }

    suspend fun deleteAllRoutines() {
        routineDao.deleteAllRoutines()
    }
}