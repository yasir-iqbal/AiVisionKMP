package com.apps.aivisioncmp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = PrimaryVariant,
    tertiary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = White,
    onSecondary = OnSecondaryDark,
    error = ErrorColor,
    onTertiary = OnPrimaryDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnBackgroundDark,
    outline = outlineDark

    /*  ,
      onSurface = OnSurfaceDark,
      onBackground = OnBackgroundDark
      error = ErrorColor,
      */
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PrimaryVariant,
    tertiary = Secondary,
    background = Background,
    surface = Surface,
    onPrimary = White,
    onSecondary = OnSecondary,
    error = ErrorColor,
    onTertiary = OnPrimary,
    onSurface = OnSurface,
    onSurfaceVariant = OnBackground,
    outline = outline
    /*  onBackground = OnBackground,
      onSurface = OnSurface,
      error = ErrorColor*/
)

@Composable
fun AIVisionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}