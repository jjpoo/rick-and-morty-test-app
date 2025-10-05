package com.android.rick.morty.test.app.domain.usecase

import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavouriteStatusUseCaseTest {

    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var useCase: GetFavouriteStatusUseCase

    @Before
    fun setup() {
        favouritesRepository = mockk()
        useCase = GetFavouriteStatusUseCase(favouritesRepository)
    }

    @Test
    fun `invoke returns true when character is favourite`() = runBlocking {
        coEvery { favouritesRepository.getCharacterFavStatus(1) } returns flow { emit(true) }

        val result = useCase.invoke(1).first()

        assertEquals(true, result)
    }

    @Test
    fun `invoke returns false when character is not favourite`() = runBlocking {
        coEvery { favouritesRepository.getCharacterFavStatus(1) } returns flow { emit(false) }

        val result = useCase.invoke(1).first()

        assertEquals(false, result)
    }
}