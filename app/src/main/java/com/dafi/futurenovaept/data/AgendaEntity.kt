package com.dafi.futurenovaept.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agenda_table")
data class AgendaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val evento: String
)