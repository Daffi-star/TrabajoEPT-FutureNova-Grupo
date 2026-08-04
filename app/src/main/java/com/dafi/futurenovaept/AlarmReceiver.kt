package com.dafi.futurenovaept

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val nombreAlarma = intent.getStringExtra("ALARM_NAME") ?: "Alarma"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "alarm_channel_id"

        // Crear canal de notificación (Obligatorio para Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarmas de FutureNova",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para las alarmas programadas"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Construir y mostrar la notificación
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("¡Es hora!")
            .setContentText("Alarma: $nombreAlarma")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(nombreAlarma.hashCode(), notification)
    }
}