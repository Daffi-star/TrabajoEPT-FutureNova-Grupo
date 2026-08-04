package com.dafi.futurenovaept

data class Receta(
    val titulo: String,
    val descripcion: String,
    val nivelRiesgo: String,
    val ingredientes: String,
    val preparacion: String,
    val comoAyuda: String,
    val datosExtra: String
)