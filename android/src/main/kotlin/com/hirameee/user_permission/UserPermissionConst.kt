package com.hirameee.user_permission

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.AppOpsManager
import android.os.Build
import android.provider.Settings

enum class UserPermissionType(
    val typeName: String,
    val stateName: String,
    val settingAction: String,
    val withPackage: Boolean,
) {
    USAGE_STATS(
        "usageStats",
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Settings.ACTION_USAGE_ACCESS_SETTINGS,
        true,
    ),
    SYSTEM_ALERT_WINDOW(
        "systemAlertWindow",
        AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        true,
    ),

    @TargetApi(Build.VERSION_CODES.O)
    PICTURE_IN_PICTURE_SETTINGS(
        "pictureInPicture",
        AppOpsManager.OPSTR_PICTURE_IN_PICTURE,
        "android.settings.PICTURE_IN_PICTURE_SETTINGS",
        true,
    ),
    WRITE_SETTINGS(
        "writeSettings",
        AppOpsManager.OPSTR_WRITE_SETTINGS,
        Settings.ACTION_MANAGE_WRITE_SETTINGS,
        true,
    ),

    @TargetApi(Build.VERSION_CODES.S)
    SCHEDULE_EXACT_ALARM(
        "scheduleExactAlarm",
        AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED,
        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
        true,
    ),
    ACCESSIBILITY_SETTINGS(
        "accessibilitySettings",
        "",
        Settings.ACTION_ACCESSIBILITY_SETTINGS,
        false,
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

enum class UserPermissionState(private val value: Int) {
    DENIED(0),
    GRANTED(1);

    fun value(): Int = value
}
