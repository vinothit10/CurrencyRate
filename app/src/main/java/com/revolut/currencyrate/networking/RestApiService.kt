package com.revolut.currencyrate.networking

import com.revolut.currencyrate.model.Rates
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface RestApiService {

    @GET("{path}")
    fun getRates(@Path("path") path: String, @Query("base") query: String): Deferred<Rates>

    companion object {

        val BASE_URL: String = "https://revolut.duckdns.org/"
        var gson = GsonBuilder()
            .setLenient()
            .create()

        fun createCurrencyService(): RestApiService {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RestApiService::class.java)
        }
    }
}