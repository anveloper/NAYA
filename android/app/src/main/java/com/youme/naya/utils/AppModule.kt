package com.youme.naya.utils

import android.content.Context
import androidx.room.Room
import com.youme.naya.database.CardDatabase
import com.youme.naya.database.dao.CardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCardDao(cardDatabase: CardDatabase): CardDao = cardDatabase.cardDao()

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): CardDatabase =
        Room.databaseBuilder(context, CardDatabase::class.java, "cards_database")
            .fallbackToDestructiveMigration().build()

}