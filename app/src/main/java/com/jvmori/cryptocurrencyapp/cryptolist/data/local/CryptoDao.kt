package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable

abstract class CryptoDao {

    @Query("Select * from crypto_table")
    abstract fun getCryptocurrencies() : Observable<List<CryptocurrencyData>>

    @Transaction
    fun update(data: List<CryptocurrencyData>) {
        if (data.isNotEmpty()) {
            delete()
            insert(data)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(cryptocurrencies : List<CryptocurrencyData>)

    @Query("Delete from crypto_table")
    abstract fun delete()
}
