package com.apps.aivisioncmp

import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppAppTheme(content: @Composable () -> Unit)
{
    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )) {
        content()
    }
}