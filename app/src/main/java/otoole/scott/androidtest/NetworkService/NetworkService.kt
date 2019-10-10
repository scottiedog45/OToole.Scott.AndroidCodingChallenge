package otoole.scott.androidtest.NetworkService

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import okhttp3.OkHttpClient
import otoole.scott.androidtest.BookModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.coroutines.CoroutineContext

val BASE_URL = "https://de-coding-test.s3.amazonaws.com"

interface NetworkService {

    companion object {

        @Volatile
        private var INSTANCE : NetworkService? = null

        fun getInstance() : NetworkService =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: invoke().also { INSTANCE = it }
            }

        operator fun invoke() : NetworkService {

            val httpClient = OkHttpClient.Builder().build()

            val gson = GsonBuilder().create()

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(NetworkService::class.java)
        }
    }

    @GET("/books.json")
    suspend fun getBookList() : List<BookModel>
}