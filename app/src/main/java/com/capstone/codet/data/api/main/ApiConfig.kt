package com.capstone.codet.data.api.main

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        private var base_url = "https://main-api-854314759095.asia-southeast2.run.app/api/"

        fun getApiService(token: String): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}

