package com.rali.timelane.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rali.checkyourlife.Activity
import com.rali.timelane.presentation.routineButton.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Insert
    suspend fun insertRoutine(routine: Routine)

    @Query("SELECT * FROM routine")
    fun getAllRoutines(): Flow<List<Routine>>

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Query("DELETE FROM routine")
    suspend fun deleteAllRoutines()
}