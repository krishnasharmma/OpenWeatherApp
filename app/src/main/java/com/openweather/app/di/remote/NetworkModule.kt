package com.openweather.app.di.remote

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import com.openweather.app.data.location.LocationModel
import com.openweather.app.utils.Constants
import com.openweather.app.utils.LocationLiveData
import com.openweather.app.utils.NetworkCheckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.getBaseUrl()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context) =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).addInterceptor(
            NetworkCheckInterceptor(appContext)
        ).build()


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, BASE_URL: String) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiInteractorImpl: ApiInteractorImpl): ApiInteractor = apiInteractorImpl

    @Provides
    fun provideLocationLiveData(@ApplicationContext appContext: Context): LiveData<LocationModel> = LocationLiveData(appContext)
}