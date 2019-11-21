package com.capps.themoviedb.data.network

import com.capps.themoviedb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The [MovieDBServices] is used to create a instance of retroffit
 * with the services defined in [MovieDBApi].
 */
object MovieDBServices {

    /**
     * This method create and return the instance of [MovieDBApi].
     * @return [MovieDBApi]
     */
    fun getInstance(): MovieDBApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = getLevel()

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.URL_BASE)
            .client(client)
            .build()
            .create(MovieDBApi::class.java)
    }

    /**
     * This method return a level depending of [BuildConfig.DEBUG]
     * value.
     *
     * If the application is in release mode, the services logs
     * are not displayed.
     *
     * @return [HttpLoggingInterceptor.Level]
     */
    private fun getLevel(): HttpLoggingInterceptor.Level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }

}