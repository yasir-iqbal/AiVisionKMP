package com.apps.aivisioncmp.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

actual fun platformTextStyle(): TextStyle = TextStyle()

@Composable
actual fun PlatformAdaptiveText(content: String)= @androidx.compose.runtime.Composable {Text(content)}