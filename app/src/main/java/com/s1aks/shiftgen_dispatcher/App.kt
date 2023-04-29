package com.s1aks.shiftgen_dispatcher

import android.app.Application
import com.s1aks.shiftgen_dispatcher.di.dataAccessModule
import com.s1aks.shiftgen_dispatcher.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(dataAccessModule, useCasesModule)
        }
    }
}