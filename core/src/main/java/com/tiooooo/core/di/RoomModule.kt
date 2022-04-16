package com.tiooooo.core.di

import androidx.room.Room
import com.tiooooo.core.data.local.room.AppDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

fun createDatabase(scope: Scope): AppDatabase {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("tiooooo".toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(
        scope.androidContext(),
        AppDatabase::class.java, "film.db"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()
}