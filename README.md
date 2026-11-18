# JARVIS Voice Controller — Android prototype

Kotlin Android starter for Poco X6 Pro / HyperOS 2. Wake response: “जी मालिक, बताइए।”

Included: HUD-inspired dark UI, manual Hindi-first Android speech input, basic Settings/Wi-Fi/Bluetooth/Back commands, Accessibility service declaration, foreground microphone-service shell, and GitHub Actions debug APK workflow.

Not implemented: always-listening Hey Jarvis detection, sherpa-onnx streaming, offline Hindi/English ASR, Gemini client, and a safe app-control command layer. The wake service currently only displays a foreground notification. Android 14+ and HyperOS restrict background microphone operation.

## Build
Open Actions and run **Android APK Build** or push to main. Download artifact `jarvis-debug-apk` after success. Local build requires Android Studio and JDK 17.

Never commit Gemini keys. Keep explicit confirmation before payments, OTPs, passwords, purchases, destructive actions, or sending messages.
