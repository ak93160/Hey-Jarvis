package com.arvind.jarvis

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

/** Foreground-service shell only. It does NOT detect “Hey Jarvis” yet. */
class WakeWordService : Service() {
    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= 26) manager.createNotificationChannel(
            NotificationChannel("jarvis_wake", "JARVIS Wake Service", NotificationManager.IMPORTANCE_LOW))
        val builder = if (Build.VERSION.SDK_INT >= 26) Notification.Builder(this, "jarvis_wake")
            else @Suppress("DEPRECATION") Notification.Builder(this)
        val notification = builder.setContentTitle("JARVIS Voice Controller")
            .setContentText("Prototype active; wake-word model is not installed.")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now).build()
        startForeground(41, notification)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_NOT_STICKY
    override fun onBind(intent: Intent?): IBinder? = null
}
