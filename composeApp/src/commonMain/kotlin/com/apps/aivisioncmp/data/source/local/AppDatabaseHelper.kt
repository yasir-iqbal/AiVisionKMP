package com.apps.aivisioncmp.data.source.local

import app.cash.sqldelight.db.SqlDriver
import com.apps.aivisioncmp.database.AiVisionDataBase

class AppDatabaseHelper constructor(driver: SqlDriver) {
    private val visionDataBase = AiVisionDataBase(driver)
    val visionDAO = visionDataBase.visionDBQueries
}

