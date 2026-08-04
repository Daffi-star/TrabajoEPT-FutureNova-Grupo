package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(record: WaterEntity)

    // Traer todos los registros para armar el gráfico de la semana
    @Query("SELECT * FROM water_records ORDER BY fecha ASC")
    suspend fun getAllWaterRecords(): List<WaterEntity>
}