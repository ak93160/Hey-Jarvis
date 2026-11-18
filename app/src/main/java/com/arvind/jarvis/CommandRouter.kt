package com.arvind.jarvis

import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.Locale

object CommandRouter {
    fun route(context: Context, raw: String): String {
        val command = raw.lowercase(Locale.forLanguageTag("hi-IN"))
        return when {
            command.contains("settings") || command.contains("सेटिंग") -> {
                context.startActivity(Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                "Settings खोल रहा हूँ।"
            }
            command.contains("wifi") || command.contains("वाईफाई") -> {
                context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                "Wi-Fi Settings खोल रहा हूँ।"
            }
            command.contains("bluetooth") || command.contains("ब्लूटूथ") -> {
                context.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                "Bluetooth Settings खोल रहा हूँ।"
            }
            command.contains("back") || command.contains("पीछे") -> {
                val ok = JarvisAccessibilityService.instance?.performGlobalAction(
                    android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK) == true
                if (ok) "पीछे जा रहा हूँ।" else "पहले JARVIS Accessibility Service चालू करें।"
            }
            else -> "यह command अभी prototype में उपलब्ध नहीं है।"
        }
    }
}
