package com.example.empiricomecampus.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.empiricomecampus.R
import com.example.empiricomecampus.views.HelperActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random


private const val CHANNEL_ID = "ALERTS"

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("NEW_TOKEN", p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, HelperActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("OBAVJEŠTENJE!")
            .setContentText("Novo obajveštenje na oglasnoj tabli!")

            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(notificationID, notification)
    }


}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager) {
    val channelName = "channelName"
    val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
        description = "My channel description"
        enableLights(true)
        lightColor = Color.GREEN
    }
    notificationManager.createNotificationChannel(channel)
}