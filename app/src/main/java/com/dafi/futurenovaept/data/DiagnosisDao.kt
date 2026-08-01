package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiagnosisDao {
    // Para guardar un nuevo registro
    @Insert
    suspend fun insert(record: DiagnosisRecord)

    // Para obtener todos los registros (ordenados del más reciente al más antiguo)
    @Query("SELECT * FROM diagnosis_records ORDER BY date DESC")
    suspend fun getAllRecords(): List<DiagnosisRecord>

    // En DiagnosisDao.kt
    @Query("SELECT * FROM diagnosis_records ORDER BY id DESC LIMIT 1")
    suspend fun getLatestDiagnosis(): DiagnosisRecord?
}