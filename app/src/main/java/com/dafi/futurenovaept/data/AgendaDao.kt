package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AgendaDao {
    @Query("SELECT COUNT(*) FROM agenda_table")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgenda(agenda: AgendaEntity)

    @Query("DELETE FROM agenda_table WHERE id = :agendaId")
    suspend fun deleteAgendaById(agendaId: String)
}