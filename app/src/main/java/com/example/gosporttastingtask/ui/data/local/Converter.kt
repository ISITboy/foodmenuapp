package com.example.gosporttastingtask.ui.data.local

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return list.joinToString(",")
    }
}