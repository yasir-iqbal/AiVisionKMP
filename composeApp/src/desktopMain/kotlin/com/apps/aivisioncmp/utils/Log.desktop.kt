package com.apps.aivisioncmp.utils

actual class KMPLogger actual constructor() {
    actual fun debug(tag: String, message: String) {
        println("[$tag] $message")
    }
    actual fun error(tag: String, message: String, throwable: Throwable?) {
        System.err.println("ERROR [$tag] $message")
        throwable?.printStackTrace()
    }
}