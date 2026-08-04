package com.dafi.futurenovaept.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_records")
data class WaterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fecha: String,      // Para identificar el día (ej. "2026-08-03")
    val mililitros: Int     // Cantidad tomada
)