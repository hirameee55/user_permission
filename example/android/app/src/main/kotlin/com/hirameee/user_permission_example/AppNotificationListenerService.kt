package com.hirameee.user_permission_example

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class AppNotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {}

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {}
}