package com.dafi.futurenovaept

fun main(){
    val logger = ConsoleLogger()
    logger.log("Mensaje de registro")
    logger.error("Error ocurrido")

    val userSyncWorker = UserSyncWorker()
    userSyncWorker.sync()

    val productSyncWorker = ProductSyncWorker()
    productSyncWorker.sync()
}

interface Logger {
    fun log(message: String)
    fun error(message: String) = log("Error: $message")
}

class ConsoleLogger : Logger {
    override fun log(message: String) {
        println(message)
    }

}

abstract class BaseSyncWorker {
    fun sync(){
        connect()
        fetchdata()
    }
    private fun connect(){
        println("Connecting...")
    }
    protected abstract fun fetchdata()

}

class UserSyncWorker : BaseSyncWorker(){
    override fun fetchdata() {
        println("Fetching user data...")
    }
}

class ProductSyncWorker : BaseSyncWorker(){
    override fun fetchdata() {
        println("Fetching product data...")
    }
}