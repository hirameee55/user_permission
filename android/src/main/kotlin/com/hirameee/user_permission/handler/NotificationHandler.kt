package com.hirameee.user_permission.handler

import android.app.Activity
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.database.ContentObserver
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.provider.Settings.Secure
import android.util.Log
import com.hirameee.user_permission.UserPermissionState

class NotificationHandler(context: Context) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun isNotificationPolicyAccessGranted(activity: Activity): UserPermissionState {
        val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            notificationManager.isNotificationListenerAccessGranted(
                ComponentName(
                    activity.packageName,
                    activity.packageName + "." + "AppNotificationListenerService"
                )
            )
        } else {
            notificationManager.isNotificationPolicyAccessGranted
        }

        return if (state) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    private var contentObserver: ContentObserver =
        object : ContentObserver(Looper.myLooper()?.let { Handler(it) }) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)

                Log.d("@@@", "aaaaa")
            }
        }

    fun aaa(context: Context) {
        context.contentResolver.notifyChange(
            //"enabled_notification_assistant"
            //Settings.Secure.getUriFor("enabled_notification_listeners"),
            //Settings.Secure.getUriFor("enabled_notification_listeners"),
            Settings.Secure.getUriFor(Secure.CONTENT_URI, "enabled_notification_listeners"),
            contentObserver
        )
    }
}