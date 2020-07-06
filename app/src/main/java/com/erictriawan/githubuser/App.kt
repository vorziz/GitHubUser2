package com.erictriawan.githubuser

import android.app.Application
import com.erictriawan.githubuser.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

    private fun init() {
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}