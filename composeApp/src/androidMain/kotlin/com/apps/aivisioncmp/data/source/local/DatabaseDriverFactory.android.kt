package com.apps.aivisioncmp.data.source.local
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.apps.aivisioncmp.database.AiVisionDataBase

actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AiVisionDataBase.Schema,
            context = androidContextForDI,
            name = "AiVisionDataBase.db"
        )
    }
}

lateinit var androidContextForDI: Context

fun initAndroidContextForDI(context: Context) {
    androidContextForDI = context
}