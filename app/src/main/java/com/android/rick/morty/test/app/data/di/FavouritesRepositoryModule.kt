package com.android.rick.morty.test.app.data.di

import com.android.rick.morty.test.app.data.repository.FavouritesRepositoryImpl
import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouritesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavouritesRepository(
        impl: FavouritesRepositoryImpl
    ): FavouritesRepository
}