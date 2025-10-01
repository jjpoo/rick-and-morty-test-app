package com.android.rick.morty.test.app.domain

import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetCharacterByIdUseCaseTest {

    private lateinit var repository: CharacterRepository
    private lateinit var useCase: GetCharacterByIdUseCase

    private val testCharacterRick = Character(
        id = 1,
        name = "Rick Sanchez",
        image = "",
        status = Status.ALIVE,
        species = Species.HUMAN,
        gender = Gender.MALE,
        type = "",
        origin = "Earth",
        location = "",
        created = "11/4/2017",
        episodes = listOf(),
        isFavorite = false
    )

    private val testCharacterMorty = Character(
        id = 2,
        name = "Rick Sanchez",
        image = "",
        status = Status.ALIVE,
        species = Species.HUMAN,
        gender = Gender.MALE,
        type = "",
        origin = "Earth",
        location = "",
        created = "11/4/2017",
        episodes = listOf(),
        isFavorite = false
    )

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCharacterByIdUseCase(repository)
    }

    @Test
    fun `invoke returns character flow from repository`() = runTest {
        val characterId = 1
        every { repository.getCharacterById(characterId) } returns flowOf(testCharacterRick)

        val result = useCase(characterId).first()

        assertEquals(testCharacterRick, result)
        verify(exactly = 1) { repository.getCharacterById(characterId) }
    }

    @Test
    fun `invoke returns null when character not found`() = runTest {
        val characterId = 999
        every { repository.getCharacterById(characterId) } returns flowOf(null)

        val result = useCase(characterId).first()

        assertNull(result)
        verify(exactly = 1) { repository.getCharacterById(characterId) }
    }

    @Test
    fun `invoke emits multiple values when repository emits multiple values`() = runTest {
        val characterId = 1
        val updatedCharacter = testCharacterRick.copy(status = Status.DEAD)

        every { repository.getCharacterById(characterId) } returns flowOf(testCharacterRick, updatedCharacter)

        val results = useCase(characterId).toList()

        assertEquals(2, results.size)
        assertEquals(testCharacterRick, results[0])
        assertEquals(updatedCharacter, results[1])
    }

    @Test
    fun `invoke handles different character ids correctly`() = runTest {
        val characterId1 = 1
        val characterId2 = 2

        every { repository.getCharacterById(characterId1) } returns flowOf(testCharacterRick)
        every { repository.getCharacterById(characterId2) } returns flowOf(testCharacterMorty)

        val result1 = useCase(characterId1).first()
        val result2 = useCase(characterId2).first()

        assertEquals(testCharacterRick, result1)
        assertEquals(testCharacterMorty, result2)
        verify(exactly = 1) { repository.getCharacterById(characterId1) }
        verify(exactly = 1) { repository.getCharacterById(characterId2) }
    }

    @Test
    fun `invoke returns favorite character correctly`() = runTest {
        val characterId = 1
        val favoriteCharacter = testCharacterRick.copy(isFavorite = true)
        every { repository.getCharacterById(characterId) } returns flowOf(favoriteCharacter)

        val result = useCase(characterId).first()

        assertEquals(true, result?.isFavorite)
    }

    @Test
    fun `invoke passes negative id to repository`() = runTest {
        val characterId = -1
        every { repository.getCharacterById(characterId) } returns flowOf(null)

        val result = useCase(characterId).first()

        assertNull(result)
        verify(exactly = 1) { repository.getCharacterById(characterId) }
    }

    @Test
    fun `invoke passes zero id to repository`() = runTest {
        val characterId = 0
        every { repository.getCharacterById(characterId) } returns flowOf(null)

        val result = useCase(characterId).first()

        assertNull(result)
        verify(exactly = 1) { repository.getCharacterById(characterId) }
    }
}