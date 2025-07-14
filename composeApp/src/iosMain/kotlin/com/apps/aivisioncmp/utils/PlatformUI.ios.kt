package com.apps.aivisioncmp.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import platform.Foundation.NSData
import platform.Foundation.NSDataBase64DecodingIgnoreUnknownCharacters
import platform.Foundation.create
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import platform.posix.memcpy
import kotlinx.cinterop.*
import org.jetbrains.skia.Image
import platform.posix.memcpy

actual fun platformTextStyle(): TextStyle = TextStyle()

@Composable
actual fun PlatformAdaptiveText(content: String) {Text( modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),text = content)}

actual suspend fun decodeBase64ToImageBitmap(base64: String): ImageBitmap? {
    return try {
        val nsData: NSData = NSData.create(base64, NSDataBase64DecodingIgnoreUnknownCharacters)
            ?: return null
        val byteArray = nsData.toByteArray()
        val skiaImage = Image.makeFromEncoded(byteArray)
        skiaImage.toComposeImageBitmap()
    } catch (e: Exception) {
        null
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val data = ByteArray(this.length.toInt())
    memScoped {
        val buffer = data.refTo(0).getPointer(this)
        memcpy(buffer, this@toByteArray.bytes, this@toByteArray.length)
    }
    return data
}