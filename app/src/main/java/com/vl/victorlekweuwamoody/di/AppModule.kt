package com.vl.victorlekweuwamoody.di

import android.app.Application
import androidx.room.Room
import com.vl.victorlekweuwamoody.api.HomeApi
import com.vl.victorlekweuwamoody.data.HomeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* This app module file contains the injectable variables using Hilt for dependency injection.
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi =
        retrofit.create(HomeApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app: Application): HomeDatabase =
        Room.databaseBuilder(app, HomeDatabase::class.java, "Home_database")
            .fallbackToDestructiveMigration()
            .build()
}