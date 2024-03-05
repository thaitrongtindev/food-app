package com.example.foodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class MealTypeConvert {
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        return attribute?.toString() ?: ""
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any? {
        return attribute
    }
}