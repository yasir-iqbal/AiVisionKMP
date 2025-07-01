package com.apps.aivisioncmp.utils

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual class LocaleManager(private val context: Context) {
    actual fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

    }

}

@Composable
fun rememberLocaleManager(): LocaleManager {
    val context = LocalContext.current
    return remember { LocaleManager(context) }
}