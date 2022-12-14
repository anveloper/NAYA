package com.youme.naya.di

import android.content.Context
import com.youme.naya.database.repository.SharedPrefDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SharedPrefModule {
    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPrefDataSource {
        return SharedPrefDataSource(context)
    }
}