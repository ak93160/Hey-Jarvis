package com.arvind.jarvis

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var status: TextView
    private val requestSpeech = 701
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(4, 12, 28)
        window.navigationBarColor = android.graphics.Color.rgb(4, 12, 28)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(android.graphics.Color.rgb(4, 12, 28))
        }
        layout.addView(TextView(this).apply {
            text = "J.A.R.V.I.S"; textSize = 30f
            setTextColor(android.graphics.Color.rgb(0, 220, 255))
        })
        layout.addView(TextView(this).apply {
            text = "VOICE CONTROL SYSTEM • PROTOTYPE 0.1"; textSize = 12f
            setTextColor(android.graphics.Color.LTGRAY); setPadding(0, 8, 0, 28)
        })
        status = TextView(this).apply {
            text = "जी मालिक, बताइए।\n\nVoice button से command बोलें।"
            textSize = 18f; setTextColor(android.graphics.Color.WHITE); setPadding(0, 12, 0, 24)
        }
        layout.addView(status)
        layout.addView(button("🎙  बोलकर कमांड दें") { startVoiceInput() })
        layout.addView(button("♿  Accessibility सेटिंग खोलें") {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        })
        layout.addView(button("🔊  Wake service (prototype) शुरू करें") {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                startForegroundService(Intent(this, WakeWordService::class.java))
                status.text = "Service शुरू हुई। Hey Jarvis detection अभी उपलब्ध नहीं है।"
            } else requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 702)
        })
        layout.addView(button("⏹  Wake service बंद करें") {
            stopService(Intent(this, WakeWordService::class.java)); status.text = "Wake service बंद।"
        })
        layout.addView(button("ℹ  सुरक्षा और सीमाएँ") {
            status.text = "Payment, OTP, password और अंतिम संदेश भेजने जैसे कामों में confirmation जरूरी होगा।"
        })
        setContentView(layout)
    }
    private fun button(label: String, action: () -> Unit) = Button(this).apply {
        text = label; textSize = 15f; setOnClickListener { action() }
    }
    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "hi-IN")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "जी मालिक, बताइए। / Speak a command")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        }
        try { startActivityForResult(intent, requestSpeech) }
        catch (_: Exception) { Toast.makeText(this, "Speech Recognition उपलब्ध नहीं है।", Toast.LENGTH_LONG).show() }
    }
    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestSpeech && resultCode == RESULT_OK) {
            val spoken = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull().orEmpty()
            status.text = "आपने कहा: " + spoken + "\n\n" + CommandRouter.route(this, spoken)
        }
    }
}
