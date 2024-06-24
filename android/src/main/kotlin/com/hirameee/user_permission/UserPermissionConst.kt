package com.hirameee.user_permission

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.AppOpsManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

enum class UserPermissionType(
    val typeName: String,
    val ops: String,
    val action: String
) {
    USAGE_STATS(
        "usageStats",
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Settings.ACTION_USAGE_ACCESS_SETTINGS
    ),
    SYSTEM_ALERT_WINDOW(
        "systemAlertWindow",
        AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
    ),

    @TargetApi(Build.VERSION_CODES.O)
    PICTURE_IN_PICTURE_SETTINGS(
        "pictureInPicture",
        AppOpsManager.OPSTR_PICTURE_IN_PICTURE,
        "android.settings.PICTURE_IN_PICTURE_SETTINGS",
    ),
    WRITE_SETTINGS(
        "writeSettings",
        AppOpsManager.OPSTR_WRITE_SETTINGS,
        Settings.ACTION_MANAGE_WRITE_SETTINGS,
    ),

    @TargetApi(Build.VERSION_CODES.S)
    SCHEDULE_EXACT_ALARM(
        "scheduleExactAlarm",
        AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED,
        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
    );

    companion object {
        fun getItem(name: String): UserPermissionType? {
            for (item in enumValues<UserPermissionType>()) {
                if (item.typeName == name) {
                    return item
                }
            }
            return null
        }
    }
}

enum class UserPermissionState(val id: Int) {
    DENIED(0),
    GRANTED(1);

    fun id(): Int {
        return id
    }
}
