package com.apps.aivisioncmp.utils

actual class KMPLogger actual constructor() {
    actual fun debug(tag: String, message: String) {
        println("[$tag] $message") // Uses iOS NSLog internally
    }
    actual fun error(tag: String, message: String, throwable: Throwable?) {
        println("ERROR [$tag] $message ${throwable?.message ?: ""}")
    }
}