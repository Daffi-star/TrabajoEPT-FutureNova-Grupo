package com.dafi.futurenovaept.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suplementos_table")
data class SuplementoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String
)