package com.dafi.futurenovaept

import androidx.room.TypeConverter

class Converters {
    // Para BooleanArray (lo convertimos a String)
    @TypeConverter
    fun fromBooleanArray(value: BooleanArray): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toBooleanArray(value: String): BooleanArray {
        return value.split(",").map { it.toBoolean() }.toBooleanArray()
    }
}