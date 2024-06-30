package com.hirameee.user_permission.handler

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.hirameee.user_permission.UserPermissionPlugin
import com.hirameee.user_permission.UserPermissionType

class IntentHandler {
    private var activity: Activity? = null

    fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    fun sendMyApp(myClass: String?) {
        try {
            activity?.let {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.setPackage(it.packageName)
                intent.setClassName(
                    it.packageName,
                    it.packageName + "." + (myClass ?: "MainActivity")
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                it.startActivity(intent)
            }
        } catch (e: ActivityNotFoundException) {
            Log.w(UserPermissionPlugin.TAG, e)
        }
    }

    fun send(permission: UserPermissionType) {
        try {
            activity?.let {
                val intent = Intent(permission.settingAction)
                if (permission.withPackage) {
                    intent.data = Uri.fromParts("package", it.packageName, null)
                }

                if (permission == UserPermissionType.NOTIFICATION_LISTENER_SERVICE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        intent.putExtra(
                            Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME,
                            ComponentName(
                                it.packageName,
                                it.packageName + "." + "AppNotificationListenerService"
                            ).flattenToString()
                        )
                    }
                }

                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                it.startActivity(intent)
            }
        } catch (e: ActivityNotFoundException) {
            Log.w(UserPermissionPlugin.TAG, e)
        }
    }
}