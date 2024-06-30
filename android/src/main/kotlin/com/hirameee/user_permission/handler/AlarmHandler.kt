package com.hirameee.user_permission.handler

import android.annotation.TargetApi
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.hirameee.user_permission.UserPermissionCallback
import com.hirameee.user_permission.UserPermissionState

class AlarmHandler(context: Context) {
    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private lateinit var callback: UserPermissionCallback

    @TargetApi(Build.VERSION_CODES.S)
    fun canScheduleExactAlarms(): UserPermissionState {
        return if (alarmManager.canScheduleExactAlarms()) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    private val onChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            callback.onChanged()
        }
    }

    @TargetApi(Build.VERSION_CODES.S)
    fun startWatchingBroadcast(context: Context, ops: String, callback: UserPermissionCallback) {
        this.callback = callback

        val intentFilter = IntentFilter()
        intentFilter.addAction(ops)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(onChangeReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(onChangeReceiver, intentFilter)
        }
    }
}