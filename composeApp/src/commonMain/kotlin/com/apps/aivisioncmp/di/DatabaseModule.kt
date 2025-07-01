package com.apps.aivisioncmp.di

import com.apps.aivisioncmp.data.source.local.AppDatabaseHelper
import com.apps.aivisioncmp.data.source.local.DatabaseDriverFactory
import com.apps.aivisioncmp.data.source.local.LocalDataSource
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseDriverFactory().createDriver() }
    single { AppDatabaseHelper(get()) }
    single { LocalDataSource(get()) }
}