package com.tiooooo.nontonyuk

import android.app.Application
import com.tiooooo.core.di.databaseModule
import com.tiooooo.core.di.repositoryModule
import com.tiooooo.core.di.retrofitModule
import com.tiooooo.nontonyuk.di.useCaseModule
import com.tiooooo.nontonyuk.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    retrofitModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }

        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "[TIMBER] %s:%s:%s ",
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber
                )
            }
        })
    }
}