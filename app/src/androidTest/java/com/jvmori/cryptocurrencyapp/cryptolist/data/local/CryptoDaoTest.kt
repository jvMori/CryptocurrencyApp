package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jvmori.cryptocurrencyapp.util.TestUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CryptoDaoTest {
    private lateinit var cryptoDao: CryptoDao
    private lateinit var cryptoDb: CryptoDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cryptoDb = Room.inMemoryDatabaseBuilder(
            context, CryptoDatabase::class.java
        ).build()
        cryptoDao = cryptoDb.cryptoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cryptoDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCryptocurrencies_readFromDb_cryptocurrenciesNotEmpty() {
        val cryptocurrencies = TestUtil.cryptocurrencies
        cryptoDao.insert(cryptocurrencies)

        fun getQuery(sort: String): SimpleSQLiteQuery {
            return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
        }

        val getCryptocurrencyList =
            cryptoDao.getCryptocurrencies(getQuery(cryptoName))

        assertNotNull(getCryptocurrencyList)
    }
}