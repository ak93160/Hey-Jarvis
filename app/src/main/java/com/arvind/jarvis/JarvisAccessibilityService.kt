package com.arvind.jarvis

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class JarvisAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() { instance = this }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}
    fun swipeUp() {
        val path = Path().apply { moveTo(500f, 1500f); lineTo(500f, 500f) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 350)).build()
        dispatchGesture(gesture, null, null)
    }
    override fun onDestroy() {
        if (instance === this) instance = null
        super.onDestroy()
    }
    companion object {
        @Volatile var instance: JarvisAccessibilityService? = null
            private set
    }
}
