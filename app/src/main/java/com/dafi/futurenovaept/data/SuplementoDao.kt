package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuplementoDao {
    @Query("SELECT COUNT(*) FROM suplementos_table")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuplemento(suplemento: SuplementoEntity)

    @Query("DELETE FROM suplementos_table WHERE id = :suplementoId")
    suspend fun deleteSuplementoById(suplementoId: Long)
}