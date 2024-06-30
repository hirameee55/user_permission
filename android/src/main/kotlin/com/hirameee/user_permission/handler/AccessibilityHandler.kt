package com.hirameee.user_permission.handler

import android.content.Context
import android.view.accessibility.AccessibilityManager
import com.hirameee.user_permission.UserPermissionCallback
import com.hirameee.user_permission.UserPermissionState

class AccessibilityHandler(context: Context) {
    private var accessibilityManager: AccessibilityManager =
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

    fun accessibilityEnabled(): UserPermissionState {
        return if (accessibilityManager.isEnabled) UserPermissionState.GRANTED else UserPermissionState.DENIED
    }

    fun startWatchingAccessibility(ops: String, callback: UserPermissionCallback) {
        accessibilityManager.addAccessibilityStateChangeListener {
            callback.onChanged()
        }
    }
}