package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import com.dafi.futurenovaept.Alarma

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

    // AÑADE ESTO:
    @Query("SELECT * FROM diagnosis_records ORDER BY date DESC LIMIT 1")
    suspend fun getLastRecord(): DiagnosisRecord?
    // ...
}

@Dao
interface AlarmDao {
    // Esta versión es para tu check de "si está vacía"
    @Query("SELECT * FROM alarmas")
    suspend fun getAllAlarmasList(): List<Alarma>

    // Esta versión es para que la UI se actualice sola
    @Query("SELECT * FROM alarmas")
    fun getAllAlarmas(): kotlinx.coroutines.flow.Flow<List<Alarma>>

    @Insert
    suspend fun insert(alarma: Alarma)

    @Update
    suspend fun update(alarma: Alarma)
}