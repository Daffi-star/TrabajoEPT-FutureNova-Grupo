package com.dafi.futurenovaept

fun main() {
    listOf<Notificación>()
    NotificacionesPush("Nueva actualización", "Versión 2.0 disponible").show()
    Notificación("Bienvenido a la aplicación").show()

}
open class Notificación(
    val title: String,
) {
   open fun show() {
        println("Mostrando notificacion: $title")
    }
}

class NotificacionesPush(
    title: String,
    val payload: String,
) : Notificación(title) {
    override fun show() {
        println("Mostrando notificacion push: $title: '$payload'")

    }
}
