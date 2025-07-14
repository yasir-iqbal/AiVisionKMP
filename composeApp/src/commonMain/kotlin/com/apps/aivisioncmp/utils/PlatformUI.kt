package com.apps.aivisioncmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle

expect fun platformTextStyle(): TextStyle

@Composable
expect fun PlatformAdaptiveText(content: String)

expect suspend fun decodeBase64ToImageBitmap(base64: String): ImageBitmap?