package com.apps.aivisioncmp.data.source.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.apps.aivisioncmp.database.AiVisionDataBase
actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = AiVisionDataBase.Schema,
            name = "AiVisionDataBase.db"
        )
    }
}