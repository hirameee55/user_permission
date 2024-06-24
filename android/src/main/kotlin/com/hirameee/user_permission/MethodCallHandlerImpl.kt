package com.hirameee.user_permission

import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MethodCallHandlerImpl(
    private var userPermission: UserPermission, private var intentSender: IntentSender
) : MethodCallHandler {
    private var channel: MethodChannel? = null

    fun startListening(messenger: BinaryMessenger) {
        channel?.let {
            stopListening()
        }
        channel = MethodChannel(messenger, "com.hirameee.plugin/user_permission")
        channel!!.setMethodCallHandler(this)
    }

    fun stopListening() {
        channel ?: return

        channel!!.setMethodCallHandler(null)
        channel = null
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "state" -> {
                val type: String = call.argument("type") ?: return
                val permission = UserPermissionType.getItem(type) ?: return

                state(permission, result)
            }

            "startWatching" -> {
                val type: String = call.argument("type") ?: return
                val myClass: String? = call.argument("myClass")
                val permission = UserPermissionType.getItem(type) ?: return

                startWatching(permission, myClass, result)
            }

            else -> result.notImplemented()
        }
    }

    private fun state(permission: UserPermissionType, result: MethodChannel.Result) {
        val state = when (permission) {
            UserPermissionType.USAGE_STATS,
            UserPermissionType.SYSTEM_ALERT_WINDOW,
            UserPermissionType.PICTURE_IN_PICTURE_SETTINGS,
            UserPermissionType.WRITE_SETTINGS,
            -> {
                userPermission.checkOp(permission.ops)
            }

            UserPermissionType.SCHEDULE_EXACT_ALARM,
            -> {
                userPermission.canScheduleExactAlarms()
            }
        }
        result.success(state.id)
    }

    private fun startWatching(
        permission: UserPermissionType,
        myClass: String?,
        result: MethodChannel.Result
    ) {
        intentSender.send(permission.action)

        when (permission) {
            UserPermissionType.USAGE_STATS,
            UserPermissionType.SYSTEM_ALERT_WINDOW,
            UserPermissionType.PICTURE_IN_PICTURE_SETTINGS,
            UserPermissionType.WRITE_SETTINGS,
            -> {
                userPermission.startWatchingMode(permission.ops, object : UserPermissionCallback {
                    override fun onChange() {
                        intentSender.sendMyApp(myClass)

                        val state = userPermission.checkOp(permission.ops)
                        result.success(state.id)
                    }
                })
            }

            UserPermissionType.SCHEDULE_EXACT_ALARM,
            -> {
                Log.d("@@@", "111 ${permission.ops}")
                userPermission.startWatchingBroadcast(
                    permission.ops,
                    object : UserPermissionCallback {
                        override fun onChange() {
                            Log.d("@@@", "222")
                            intentSender.sendMyApp(myClass)

                            val state = userPermission.canScheduleExactAlarms()
                            result.success(state.id)
                        }
                    })
            }
        }
    }
}