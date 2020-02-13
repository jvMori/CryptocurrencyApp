package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module

@Database(entities = [CryptocurrencyData::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CryptoDatabase::class.java,
            "cryptocurrencyDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}