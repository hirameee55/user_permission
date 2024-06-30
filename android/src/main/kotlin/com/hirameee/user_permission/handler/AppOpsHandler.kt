package com.hirameee.user_permission.handler

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import com.hirameee.user_permission.UserPermissionCallback
import com.hirameee.user_permission.UserPermissionState

class AppOpsHandler(context: Context) {
    private var appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    private lateinit var callback: UserPermissionCallback

    fun checkOp(activity: Activity, ops: String): UserPermissionState {
        val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                ops,
                android.os.Process.myUid(),
                activity.packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                ops,
                android.os.Process.myUid(),
                activity.packageName
            )
        }

        return if (state == AppOpsManager.MODE_ALLOWED) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    private val opChangeListener = AppOpsManager.OnOpChangedListener { _, _ ->
        callback.onChanged()
    }

    fun startWatchingMode(activity: Activity, ops: String, callback: UserPermissionCallback) {
        this.callback = callback

        appOpsManager.stopWatchingMode(opChangeListener)
        appOpsManager.startWatchingMode(ops, activity.packageName, opChangeListener)
    }
}