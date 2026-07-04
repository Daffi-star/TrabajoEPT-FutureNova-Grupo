package com.dafi.futurenovaept

fun main(){

    //Condicionales

    //if
    @Suppress("unused")
     var score = 85
     if (score >= 90) { //Verifica si la condición es verdadera
        println("Excelente. Tienes una A")
    } else if (score >= 75) { //Verifica si la condición anterior es falsa y si esta es verdadera
        println("Bien. Tienes una B")
    } else { //Si ninguna de las condiciones anteriores se cumple, se ejecuta este bloque
        println("Necesitas mejorar.")
    }

    //When
    @Suppress("unused")
    val day = 3
    val dayName = when (day) { //Se utiliza para comparar un valor con varias opciones
        1 -> "Lunes"
        2 -> "Martes"
        3 -> "Miercoles"
        4 -> "Jueves"
        5 -> "Viernes"
        6 -> "Sábado"
        7 -> "Domingo"
        else -> "Día inválido"
    }
    println(dayName)

    //Blucles

    //for
    @Suppress("unused")
    val numbers = listOf("Banana", "Naranja", "Manzana", "Mango")
    for (number in numbers) { //Recorre cada elemento en la lista
        println(number)
    }

    //while
    var counter = 0
    while (counter <= 5) { //Mientras la condición sea verdadera, se ejecuta el bloque
        println("Contador: $counter")
        counter++ //Incrementa el contador en 1
    }

}