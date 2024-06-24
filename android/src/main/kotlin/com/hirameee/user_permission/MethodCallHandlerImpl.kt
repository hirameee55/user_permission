package com.hirameee.user_permission

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MethodCallHandlerImpl(
    private var userPermission: UserPermission,
    private var intentSender: IntentSender
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
            "checkOp" -> {
                val type: String = call.argument("type") ?: return
                val permission = UserPermissionType.getItem(type) ?: return

                val state = userPermission.checkOp(permission.ops)
                result.success(state)
            }

            "startWatching" -> {
                val type: String = call.argument("type") ?: return
                val myClass: String? = call.argument("myClass")
                val permission = UserPermissionType.getItem(type) ?: return

                intentSender.send(permission.action)
                userPermission.startWatchingMode(permission.ops, object : UserPermissionCallback {
                    override fun onChange() {
                        intentSender.sendMyApp(myClass)

                        val state = userPermission.checkOp(permission.ops)
                        result.success(state)
                    }
                })
            }

            else -> result.notImplemented()
        }
    }
}