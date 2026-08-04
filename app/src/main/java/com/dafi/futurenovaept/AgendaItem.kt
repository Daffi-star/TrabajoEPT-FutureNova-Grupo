package com.dafi.futurenovaept

data class AgendaItem(
    val id: String,
    val titulo: String,
    val categoria: String, // Ej: "Examen", "Lectura", "Clase"
    val fechaHora: String,
    var completada: Boolean = false
)