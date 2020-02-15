package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Observable

@Dao
interface CryptoDao {

    @RawQuery(observedEntities = [CryptocurrencyData::class])
    fun getCryptocurrencies(query : SupportSQLiteQuery) : Observable<List<CryptocurrencyData>>

//    fun getQuery(sort : String) : SimpleSQLiteQuery {
//        return  SimpleSQLiteQuery("Select * from crypto_table order by ? DESC", arrayOf(sort))
//    }

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
