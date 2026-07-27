package com.dafi.futurenovaept

fun main() {
    //Forma básica (Sin parámetros)
    val saludo = {
        println("Hola")
    }
saludo()

    //Con parámetros
    val cuadrado: (a: Int) -> Int = {x -> x * x}
    val cuadradoResultado = cuadrado(5)
    println(cuadradoResultado)

    //Con múltiples parámetros
    val suma: ( a: Int, b: Int) -> Int = {x, y -> x + y}
    val sumaResultado = suma(3, 4)
    println(sumaResultado)

    //Lambdas dentro de higher-order functions
    val nombres = listOf("Juan", "Maria", "Pedro")
    nombres.forEach {nombre ->
        println(nombre)
    }

}
