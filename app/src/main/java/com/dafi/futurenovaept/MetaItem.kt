package com.dafi.futurenovaept

data class MetaItem(
    val id: String,
    val titulo: String,
    val descripcion: String,
    var completada: Boolean = false
)