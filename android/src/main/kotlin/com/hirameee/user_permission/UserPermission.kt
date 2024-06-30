package com.hirameee.user_permission

import android.app.Activity
import android.content.Context
import com.hirameee.user_permission.handler.AccessibilityHandler
import com.hirameee.user_permission.handler.AlarmHandler
import com.hirameee.user_permission.handler.AppOpsHandler
import com.hirameee.user_permission.handler.IntentHandler
import com.hirameee.user_permission.handler.NotificationHandler
import io.flutter.plugin.common.MethodChannel

interface UserPermissionCallback {
    fun onChanged()
}

class UserPermission {
    private lateinit var intentHandler: IntentHandler
    private lateinit var alarmHandler: AlarmHandler
    private lateinit var appOpsHandler: AppOpsHandler
    private lateinit var accessibilityHandler: AccessibilityHandler
    private lateinit var notificationHandler: NotificationHandler
    private var context: Context? = null
    private var activity: Activity? = null

    fun init(context: Context) {
        this.context = context

        this.intentHandler = IntentHandler()
        this.alarmHandler = AlarmHandler(context)
        this.appOpsHandler = AppOpsHandler(context)
        this.accessibilityHandler = AccessibilityHandler(context)
        this.notificationHandler = NotificationHandler(context)
    }

    fun delete() {
        this.context = null
    }

    fun setActivity(activity: Activity?) {
        this.activity = activity
        this.intentHandler.setActivity(activity)
    }

    fun getState(
        permission: UserPermissionType,
        result: MethodChannel.Result
    ) {
        val state = when (permission) {
            UserPermissionType.USAGE_STATS,
            UserPermissionType.SYSTEM_ALERT_WINDOW,
            UserPermissionType.PICTURE_IN_PICTURE_SETTINGS,
            UserPermissionType.WRITE_SETTINGS,
            -> {
                activity?.let {
                    appOpsHandler.checkOp(it, permission.stateName)
                } ?: UserPermissionState.DENIED
            }

            UserPermissionType.SCHEDULE_EXACT_ALARM,
            -> {
                alarmHandler.canScheduleExactAlarms()
            }

            UserPermissionType.ACCESSIBILITY_SETTINGS,
            -> {
                accessibilityHandler.accessibilityEnabled()
            }

            UserPermissionType.NOTIFICATION_LISTENER_SERVICE,
            -> {
                activity?.let {
                    notificationHandler.isNotificationPolicyAccessGranted(it)
                } ?: UserPermissionState.DENIED
            }
        }
        result.success(state.value())
    }

    fun startWatching(
        permission: UserPermissionType,
        myClass: String?,
        result: MethodChannel.Result
    ) {
        intentHandler.send(permission)

        when (permission) {
            UserPermissionType.USAGE_STATS,
            UserPermissionType.SYSTEM_ALERT_WINDOW,
            UserPermissionType.PICTURE_IN_PICTURE_SETTINGS,
            UserPermissionType.WRITE_SETTINGS,
            -> {
                activity?.let {
                    appOpsHandler.startWatchingMode(it, permission.stateName,
                        object : UserPermissionCallback {
                            override fun onChanged() {
                                intentHandler.sendMyApp(myClass)

                                val state = appOpsHandler.checkOp(it, permission.stateName)
                                result.success(state.value())
                            }
                        })
                }
            }

            UserPermissionType.SCHEDULE_EXACT_ALARM,
            -> {
                context?.let {
                    alarmHandler.startWatchingBroadcast(it, permission.stateName,
                        object : UserPermissionCallback {
                            override fun onChanged() {
                                intentHandler.sendMyApp(myClass)

                                val state = alarmHandler.canScheduleExactAlarms()
                                result.success(state.value())
                            }
                        })
                }
            }

            UserPermissionType.ACCESSIBILITY_SETTINGS,
            -> {
                accessibilityHandler.startWatchingAccessibility(permission.stateName,
                    object : UserPermissionCallback {
                        override fun onChanged() {
                            intentHandler.sendMyApp(myClass)

                            val state = accessibilityHandler.accessibilityEnabled()
                            result.success(state.value())
                        }
                    })
            }

            UserPermissionType.NOTIFICATION_LISTENER_SERVICE,
            -> {
                context?.let {
                    notificationHandler.aaa(it)
                }
            }
        }
    }
}