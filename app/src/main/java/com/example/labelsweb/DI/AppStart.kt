package com.example.labelsweb.DI

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class AppStart: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@AppStart)
            modules(appModule)
        }
    }
}