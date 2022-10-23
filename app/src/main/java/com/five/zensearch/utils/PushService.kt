package com.five.zensearch.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.five.zensearch.R
import com.five.zensearch.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        remoteMessage.notification?.let {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.notification?.body))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                notify(11, builder.build())
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    companion object {
        const val CHANNEL_ID = "channel_id"
    }
}
