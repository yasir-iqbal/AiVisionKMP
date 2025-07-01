package com.apps.aivisioncmp.utils
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults

actual class LocaleManager {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    actual fun setAppLanguage(languageCode: String) {

       /* NSLocale.setCurrentLocale(NSLocale(languageCode))
        userDefaults.setObject(listOf(languageCode), forKey = "AppleLanguages")
        userDefaults.synchronize()*/
    }

}