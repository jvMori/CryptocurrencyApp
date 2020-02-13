package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Observable

@Dao
interface CryptoDao {
    @RawQuery
    fun getCryptocurrencies(query : SupportSQLiteQuery) : Observable<List<CryptocurrencyData>>

    fun getQuery(order : String) : SimpleSQLiteQuery {
        return SimpleSQLiteQuery("Select * from crypto_table order by ? DESC", arrayOf(order))
    }

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
