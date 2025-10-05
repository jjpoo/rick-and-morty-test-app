package com.android.rick.morty.test.app.domain.usecase

import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ToggleFavoritesUseCaseTest {

    private lateinit var repository: FavouritesRepository
    private lateinit var useCase: ToggleFavoritesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ToggleFavoritesUseCase(repository)
    }

    @Test
    fun `invoke when character NOT favorite ADDS character to favorites`() = runBlocking {
        coEvery { repository.getCharacterFavStatus(1) } returns flow { emit(false) }
        coEvery { repository.addToFavorites(1) } returns Unit
        coEvery { repository.removeFromFavourites(1) } returns Unit

        val result = useCase.invoke(1)

        assertEquals(true, result)
        coVerify(exactly = 1) { repository.addToFavorites(1) }
        coVerify(exactly = 0) { repository.removeFromFavourites(1) }
    }

    @Test
    fun `invoke when character IS favorite REMOVES character from favorites`() = runBlocking {
        coEvery { repository.getCharacterFavStatus(1) } returns flow { emit(true) }
        coEvery { repository.addToFavorites(1) } returns Unit
        coEvery { repository.removeFromFavourites(1) } returns Unit

        val result = useCase.invoke(1)

        assertEquals(false, result)
        coVerify(exactly = 1) { repository.removeFromFavourites(1) }
        coVerify(exactly = 0) { repository.addToFavorites(1) }
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository fails`(): Unit = runBlocking {
        coEvery { repository.getCharacterFavStatus(1) } throws Exception("DB error")

        useCase.invoke(1)
    }
}