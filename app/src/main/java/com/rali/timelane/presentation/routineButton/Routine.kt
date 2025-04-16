package com.rali.timelane.presentation.routineButton

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rali.checkyourlife.Activity

@Entity(tableName = "routine")
data class Routine (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val activities: List<Activity>,
)