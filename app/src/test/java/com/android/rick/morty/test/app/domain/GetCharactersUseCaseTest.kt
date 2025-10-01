package com.android.rick.morty.test.app.domain

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    private lateinit var repository: CharacterRepository
    private lateinit var useCase: GetCharactersUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCharactersUseCase(repository)
    }

    private val testCharacter1 = Character(
        id = 1,
        name = "Rick Sanchez",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        status = Status.ALIVE,
        species = Species.HUMAN,
        gender = Gender.MALE,
        type = "",
        origin = "Earth",
        location = "Earth",
        created = "2017-11-04T18:48:46.250Z",
        episodes = listOf("https://rickandmortyapi.com/api/episode/1"),
        isFavorite = true
    )

    private val testCharacter2 = Character(
        id = 2,
        name = "Morty Smith",
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        status = Status.ALIVE,
        species = Species.HUMAN,
        gender = Gender.MALE,
        type = "",
        origin = "Earth",
        location = "Earth",
        created = "2017-11-04T18:50:00.000Z",
        episodes = listOf("https://rickandmortyapi.com/api/episode/1"),
        isFavorite = true
    )

    @Test
    fun `invoke returns PagingData with characters`() = runTest {
        val pagingData: PagingData<Character> = PagingData.from(listOf(testCharacter1))

        coEvery { repository.getCharacters() } returns flow { emit(pagingData) }

        val result = useCase.invoke().asSnapshot()

        assertEquals(1, result.size)
        assertEquals(testCharacter1, result.first())
    }

    @Test
    fun `invoke returns PagingData with multiple characters`() = runTest {
        val pagingData: PagingData<Character> =
            PagingData.from(listOf(testCharacter1, testCharacter2))
        coEvery { repository.getCharacters() } returns flow { emit(pagingData) }

        val snapshot = useCase.invoke().asSnapshot()

        assertEquals(2, snapshot.size)
        assertEquals(testCharacter1, snapshot[0])
        assertEquals(testCharacter2, snapshot[1])
    }

    @Test
    fun `invoke returns empty PagingData`() = runTest {
        val pagingData: PagingData<Character> = PagingData.empty()
        coEvery { repository.getCharacters() } returns flow { emit(pagingData) }

        val snapshot = useCase.invoke().asSnapshot()

        assertEquals(0, snapshot.size)
    }
}