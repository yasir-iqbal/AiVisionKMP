package com.apps.aivisioncmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform