package com.dafi.futurenovaept

fun main() {
    val person = person("John", 30) //Sirve para crear una instancia de la clase
    person.greet()
}
class person( //Sirve para definir una clase
    val name: String,
    var age: Int

){
    fun greet(){ //Sirve para definir un método de la clase
        println("Hello, my name is $name and I am $age years old.")
    }
}

