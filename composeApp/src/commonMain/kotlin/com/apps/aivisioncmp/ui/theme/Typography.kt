package com.apps.aivisioncmp.ui.theme

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.barlow_font_family
import aivisionkmp.composeapp.generated.resources.varela_round
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Barlow  @Composable get() = FontFamily(
   org.jetbrains.compose.resources.Font(resource = Res.font.barlow_font_family)
)

val Montserrat @Composable get() = FontFamily(
    org.jetbrains.compose.resources.Font(resource = Res.font.varela_round,FontWeight.Medium)
)

val Typography @Composable get() = Typography(
    bodyLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        color = Surface
    ),

    bodySmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.W400,
        color = Surface
    ),

    displayLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)