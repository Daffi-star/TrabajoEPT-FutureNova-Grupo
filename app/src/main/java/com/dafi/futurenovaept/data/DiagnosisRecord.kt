package com.dafi.futurenovaept.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnosis_records")
data class DiagnosisRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val nivelRiesgo: String,    // "Bajo", "Moderado", "Alto" (para filtrar recetas)
    val respuestasRaw: String,  // Aquí guardamos las respuestas originales
    val observacion: String
)