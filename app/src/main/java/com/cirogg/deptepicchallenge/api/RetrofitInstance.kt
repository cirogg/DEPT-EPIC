package com.cirogg.deptepicchallenge.api

import com.cirogg.deptepicchallenge.utils.Const.Companion.BASE_URL
import com.cirogg.deptepicchallenge.utils.Const.Companion.READ_TIMEOUT
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val epicApi by lazy {
            retrofit.create(EpicApi::class.java)
        }
    }
}