package com.apps.aivisioncmp.utils

actual class KMPLogger actual constructor() {
    actual fun debug(tag: String, message: String) {
        android.util.Log.d(tag, message)
    }
    actual fun error(tag: String, message: String, throwable: Throwable?) {
        android.util.Log.e(tag, message, throwable)
    }
}