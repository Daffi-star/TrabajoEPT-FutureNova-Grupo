package com.dafi.futurenovaept.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metas_table")
data class MetaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String
)