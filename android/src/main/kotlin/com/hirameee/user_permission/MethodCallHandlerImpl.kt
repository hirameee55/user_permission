package com.hirameee.user_permission

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MethodCallHandlerImpl(private var userPermission: UserPermission) : MethodCallHandler {
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

                userPermission.getState(permission, result)
            }

            "startWatching" -> {
                val type: String = call.argument("type") ?: return
                val myClass: String? = call.argument("myClass")
                val permission = UserPermissionType.getItem(type) ?: return

                userPermission.startWatching(permission, myClass, result)
            }

            else -> result.notImplemented()
        }
    }
}