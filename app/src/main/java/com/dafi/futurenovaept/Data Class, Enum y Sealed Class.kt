package com.dafi.futurenovaept

fun main(){
    val user1 = User("Willy")
    val user2 = user1.copy("Juan", "juan.a.wheeler@example-pet-store.com")
    val (name, email) = user2
    println("Name: $name, Email: $email")
    println("user1 has email: ${user1.hasEmail()}")
    println("user2 has email: ${user2.hasEmail()}")

    val direction = Direction.NORTH
    println("Opposite of $direction is ${direction.opposite()}")

    val state: State = State.Loading
    val state2: State = State.Success("Data loaded")
    val state3: State = State.Error(Exception("Error loading data"))

    when(state) {
        is State.Loading -> println("Loading...")
        is State.Success -> println("Success: ${state.data}")
        is State.Error -> println("Error: ${state.exception.message}")
    }
}


data class User( //Almacena datos y se utiliza para representar una entidad
    val name: String,
    val email: String? = null
) {
    fun hasEmail() = email != null
}

enum class Direction (
    val degrees: Int
){
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270),
    ;

    fun opposite(): Direction {
        return when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }
}

sealed class State {
    object Loading : State()
    data class Success(val data: String) : State()
    data class Error(val exception: Exception) : State()
}
