package com.apps.aivisioncmp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

expect fun platformTextStyle(): TextStyle

@Composable
expect fun PlatformAdaptiveText(content: String): @Composable () -> Unit