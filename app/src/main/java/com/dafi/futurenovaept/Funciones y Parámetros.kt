package com.dafi.futurenovaept

fun main(){

    //Funciones
    @Suppress("unused")
    fun greetUser(name: String) { //Función que recibe un parámetro (name) de tipo String o texto
        println("Hola, $name!")
    }
    greetUser("Willy") //Llamada a la función con un argumento ("Willy")

    fun greetUserDeafult(name: String = "Invitado") { //Función con un valor por defecto
        println("Hola, $name!")
    }
    greetUserDeafult() //Llamada a la función sin argumentos, utilizando el valor por defecto

    fun add(a: Int, b: Int): Int { //Función que devuelve un valor entero (Int)
        return a + b
    }
    println(add(5, 3)) //Imprime el resultado de la suma (8)
}
