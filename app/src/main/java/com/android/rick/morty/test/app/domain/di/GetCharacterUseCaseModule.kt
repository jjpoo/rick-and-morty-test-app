package com.android.rick.morty.test.app.domain.di

import com.android.rick.morty.test.app.domain.GetCharactersUseCase
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetCharacterUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharactersUseCase(
        characterRepository: CharacterRepository
    ): GetCharactersUseCase {
        return GetCharactersUseCase(characterRepository)
    }
}