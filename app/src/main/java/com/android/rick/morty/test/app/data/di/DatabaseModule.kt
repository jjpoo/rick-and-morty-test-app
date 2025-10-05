package com.android.rick.morty.test.app.data.di

import android.content.Context
import com.android.rick.morty.test.app.core.DB_NAME
import com.android.rick.morty.test.app.data.local.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): CharactersDatabase {
        return androidx.room.Room.databaseBuilder(
                appContext,
                CharactersDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideCharactersDao(database: CharactersDatabase) = database.charactersDao

    @Provides
    fun provideFavouriteDao(database: CharactersDatabase) = database.favouriteDao
}