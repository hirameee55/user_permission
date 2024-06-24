package com.hirameee.user_permission

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.util.Log

interface UserPermissionCallback {
    fun onChange()
}

class UserPermission {
    private lateinit var appOpsManager: AppOpsManager
    private var context: Context? = null
    private var activity: Activity? = null
    private lateinit var callback: UserPermissionCallback

    fun init(context: Context) {
        this.context = context
        appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    }

    fun delete() {
        this.context = null
    }

    fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    fun checkOp(ops: String): Int? {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return appOpsManager.unsafeCheckOpNoThrow(
                    ops,
                    android.os.Process.myUid(),
                    it.packageName
                )
            } else {
                return appOpsManager.checkOpNoThrow(
                    ops,
                    android.os.Process.myUid(),
                    it.packageName
                )
            }
        }
        return null
    }

    private val opChangeListener = AppOpsManager.OnOpChangedListener { _, _ ->
        callback.onChange()
    }

    fun startWatchingMode(ops: String, callback: UserPermissionCallback) {
        this.callback = callback

        activity?.let {
            appOpsManager.stopWatchingMode(opChangeListener)
            appOpsManager.startWatchingMode(ops, it.packageName, opChangeListener)
        }
    }
}