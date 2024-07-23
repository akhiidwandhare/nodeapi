package com.nodeapi.retrofitdemo

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private const val BASE_URL: String = "http://10.0.2.2:8080/"
//        private val client: OkHttpClient = OkHttpClient.Builder().apply {
//            addInterceptor(
//                Interceptor { chain ->
//                    val builder = chain.request().newBuilder()
////                    Calendar.getInstance().time
////                    builder.header("Token", "Bearer ")
//                    builder.header("Content-Type", "application/json")
//                    builder.header("Access-Control-Allow-Origin", "*")
//                    builder.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE")
//                    builder.header("Access-Control-Allow-Credentials", "true")
//                    builder.header("Cache-Control", "private, no-cache, no-store")
////                    builder.header("cookie", "connect.sid=s%3AFfQtyl-8BrR7LL13PT7cgfhe1yQpo7id.RD5rtaTLKtCVbyGVJVcaRFzxHylET76Hg0EK0PInkao")
//                    return@Interceptor chain.proceed(builder.build())
//                }
//            )
//        }.build()

        private fun getClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val request: Request = original.newBuilder()
                    .header("Keep-Alive","timeout=5")
                    .header("content-length","130")
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            });

            httpClient.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

            return httpClient.build()
        }

        fun getRetrofitInstance(): Retrofit {
            Log.d("getRetrofitInstance","getRetrofitInstance")
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}