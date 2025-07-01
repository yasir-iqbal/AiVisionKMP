package com.apps.aivisioncmp.utils

expect class KMPLogger() {
    fun debug(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable? = null)
}