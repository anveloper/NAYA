package com.youme.naya.utils

import android.content.Context
import androidx.room.Room
import com.youme.naya.database.CardDatabase
import com.youme.naya.database.dao.CardDao
import com.youme.naya.database.dao.ScheduleDao
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.database.repository.ScheduleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    lateinit var appContext: Context

    @Singleton
    @Provides
    fun provideCardDao(cardDatabase: CardDatabase): CardDao = cardDatabase.cardDao()


    @Singleton
    @Provides
    fun provideScheduleDao(database: CardDatabase) : ScheduleDao {
        return database.scheduleDao()
    }

    @Singleton
    @Provides
    fun provideScheduleRepository(database: CardDatabase) : ScheduleRepository {
        return ScheduleRepositoryImpl(database.scheduleDao())
    }

    fun provide(context: Context) {
        appContext = context
    }


    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): CardDatabase =
        Room.databaseBuilder(context, CardDatabase::class.java, "cards_database")
            .fallbackToDestructiveMigration()
            .build()

}