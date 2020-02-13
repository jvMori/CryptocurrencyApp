package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.*
import io.reactivex.Observable

@Dao
interface CryptoDao {

    @Query("Select * from crypto_table order by crypto_name DESC")
    fun getCryptocurrencies() : Observable<List<CryptocurrencyData>>

    @Transaction
    fun update(data: List<CryptocurrencyData>) {
        if (data.isNotEmpty()) {
            delete()
            insert(data)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptocurrencies : List<CryptocurrencyData>)

    @Query("Delete from crypto_table")
    fun delete()
}
