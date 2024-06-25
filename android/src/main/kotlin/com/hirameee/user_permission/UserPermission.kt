package com.hirameee.user_permission

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlarmManager
import android.app.AppOpsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

interface UserPermissionCallback {
    fun onChange()
}

class UserPermission {
    private lateinit var appOpsManager: AppOpsManager
    private lateinit var alarmManager: AlarmManager
    private var context: Context? = null
    private var activity: Activity? = null
    private lateinit var callback: UserPermissionCallback

    fun init(context: Context) {
        this.context = context
        appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun delete() {
        this.context = null
    }

    fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    fun checkOp(ops: String): UserPermissionState {
        var state = AppOpsManager.MODE_DEFAULT

        activity?.let {
            state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOpsManager.unsafeCheckOpNoThrow(
                    ops,
                    android.os.Process.myUid(),
                    it.packageName
                )
            } else {
                appOpsManager.checkOpNoThrow(
                    ops,
                    android.os.Process.myUid(),
                    it.packageName
                )
            }
        }
        return if (state == AppOpsManager.MODE_ALLOWED) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    @TargetApi(Build.VERSION_CODES.S)
    fun canScheduleExactAlarms(): UserPermissionState {
        return if (alarmManager.canScheduleExactAlarms()) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    private val opChangeListener = AppOpsManager.OnOpChangedListener { _, _ ->
        callback.onChange()
    }

    private val onChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            callback.onChange()
        }
    }

    fun startWatchingMode(ops: String, callback: UserPermissionCallback) {
        activity?.let {
            this.callback = callback

            appOpsManager.stopWatchingMode(opChangeListener)
            appOpsManager.startWatchingMode(ops, it.packageName, opChangeListener)
        }
    }

    fun startWatchingBroadcast(ops: String, callback: UserPermissionCallback) {
        context?.let {
            this.callback = callback

            val intentFilter = IntentFilter()
            intentFilter.addAction(ops)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.registerReceiver(onChangeReceiver, intentFilter, Context.RECEIVER_EXPORTED)
            } else {
                it.registerReceiver(onChangeReceiver, intentFilter)
            }
        }
    }
}