package com.dafi.futurenovaept

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmas")
data class Alarma(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var hora: String,
    var activo: Boolean,
    var diasRepeticion: BooleanArray
)