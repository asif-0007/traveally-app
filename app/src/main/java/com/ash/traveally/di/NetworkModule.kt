package com.ash.traveally.di

import com.ash.traveally.api.AuthInterceptor
import com.ash.traveally.api.BlogAPI
import com.ash.traveally.api.ChatAPI
import com.ash.traveally.api.PlaceAPI
import com.ash.traveally.api.UserAPI
import com.ash.traveally.utils.Constants.BASE_URL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesPlaceAPI(okHttpClient: OkHttpClient): PlaceAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PlaceAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesBlogAPI(okHttpClient: OkHttpClient): BlogAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BlogAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesChatAPI(okHttpClient: OkHttpClient): ChatAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ChatAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesUserAPI(): UserAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideChatCollection() = Firebase.firestore.collection("chats")


    @Singleton
    @Provides
    fun provideFirebaseStorage() = Firebase.storage
}