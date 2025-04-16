package com.rali.timelane.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rali.checkyourlife.Activity

class Converters {
    @TypeConverter
    fun fromActivityList(value: List<Activity>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toActivityList(value: String): List<Activity> {
        val listType = object : TypeToken<List<Activity>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
