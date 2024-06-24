package com.hirameee.user_permission

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

class UserPermissionPlugin : FlutterPlugin, ActivityAware {

    companion object{
        const val TAG = "UserPermission"
    }

    private var methodCallHandler: MethodCallHandlerImpl
    private var userPermission: UserPermission = UserPermission()
    private var intentSender: IntentSender = IntentSender()

    init {
        methodCallHandler = MethodCallHandlerImpl(userPermission, intentSender)
    }

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        userPermission.init(flutterPluginBinding.applicationContext)
        methodCallHandler.startListening(flutterPluginBinding.binaryMessenger)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        userPermission.delete()
        methodCallHandler.stopListening()
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        userPermission.setActivity(binding.activity)
        intentSender.setActivity(binding.activity)
    }

    override fun onDetachedFromActivity() {
        userPermission.setActivity(null)
        intentSender.setActivity(null)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }
}