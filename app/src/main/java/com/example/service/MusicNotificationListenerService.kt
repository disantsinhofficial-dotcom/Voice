package com.example.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class MusicNotificationListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        // Monitor media style notifications or playback status
        sbn?.notification?.let { notif ->
            val extras = notif.extras
            val title = extras.getCharSequence("android.title")?.toString()
            val artist = extras.getCharSequence("android.text")?.toString()
            if (title != null || artist != null) {
                Log.d("MusicNotification", "Playing: $title by $artist")
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}
