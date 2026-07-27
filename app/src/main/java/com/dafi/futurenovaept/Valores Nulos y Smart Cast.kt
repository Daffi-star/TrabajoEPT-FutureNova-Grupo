package com.dafi.futurenovaept

fun main() {
    val nombre: String? = null

    // Safe call
    println("Longitud: ${nombre?.length}") // Imprime "Longitud: null"

    // Elvis operator
    val longitud = nombre?.length ?: 0
    println("Longitud con valor por defecto: $longitud") // Imprime "Longitud con valor por defecto: 0"

    // Smart cast
    val texto: Any = "Hola Kotlin"

    if (texto is String) {
        println("La cadena tiene ${texto.length} caracteres") // Imprime "La cadena tiene x caracteres"
    }
}