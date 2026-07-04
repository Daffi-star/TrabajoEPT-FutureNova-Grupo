package com.dafi.futurenovaept

fun main() {
    @Suppress("unused")
    //Enteros
    val age: Int = 30 //Numeros pequeños
    val members: Long = 1000000L //Numeros grandes

    @Suppress("unused")
    //Decimales
    val Number_pi: Float = 3.1416f //Numeros decimales
    val price: Double = 80.5097673 //Numeros decimales grandes

    @Suppress("unused")
    //Ahorro de memoria (No es necesario utilizarlo para nuestro proyecto)
    val ByteValue: Byte = 127 //Numeros enteros mas pequeños
    val ShortValue: Short = 32767 //Numeros enteros mas pequeños

    @Suppress("unused")
    //Textos
    val name: String = "Willy" //Varios caracteres
    val apodo: Char = 'W' //Solo un caracter (Siempre con comillas simples)

    @Suppress("unused")
    //Booleanos
    val isActive: Boolean = true
    val isNotActive: Boolean = false

    //Operadores Aritmeticos
    //(Println se utiliza para imprimir o "pintar" el resultado de la operacion o acciòn que quieras realizar)
    val sum: Int = 5 + 3 //Sumas
    println(sum) //Resultado de la suma

    val subtraction: Int = 10 - 4 //Restas
    println(subtraction) //Resultado de la resta

    val multiplication: Int = 7 * 3 //Multiplicaciones
    println(multiplication) //Resultado de la multiplicacion

    val division: Int = 10 / 2 //Divisiones
    println(division) //Resultado de la división

    val remainder: Int = 10 % 3 //Residuo (Módulo)
    println(remainder) //Resultado del residuo

    //Asignation
    @Suppress("unused")
    var count = 10 //Sirve para incrementar sienta cantidad en la variable
    count = count + 1 //Or: Count +=1
    println(count)

    //Se puede hacer lo mismo con otras operaciones
    count = count - 1 //Or: Count -=1
    //println(count)
    count = count * 2 //Or: Count *=2
    //println(count)
    count = count / 2 //Or: Count /=2
    //println(count)

    //Comparación
    @Suppress("unused")
    val isEquals = (5 == 5) //Esto lo que hace es verificar si lo que hay en los parentesis son iguales o no
    val isDifferent = (5 != 5) //Esto lo que hace es verificar si lo que hay en los parentesis son diferentes o no
    val isGreater = (5 > 5) //Esto lo que hace es verificar si lo que hay en los parentesis son mayores o no
    val isLess = (5 < 5) //Esto lo que hace es verificar si lo que hay en los parentesis son menores o no
    val isGreaterOrEqual = (5 >= 5) //Esto lo que hace es verificar si lo que hay en los parentesis son mayores o iguales o no
    val isLessOrEqual = (5 <= 5)
    println(isEquals)

    //Lógicos
    @Suppress("unused")
    val hasTicket = true
    val seatAviliable = false
    val canwatchMovie = hasTicket && seatAviliable
    println(canwatchMovie) //Resultado de la comparación lógica
}
