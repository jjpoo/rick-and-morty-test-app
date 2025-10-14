package com.android.rick.morty.test.app.presentation.characters

import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.domain.usecase.GetCharactersUseCase
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest {

    private lateinit var viewModel: CharactersViewModel
    private val getCharactersUseCase = mockk<GetCharactersUseCase>()

    @Before
    fun setup() {
        coEvery { getCharactersUseCase(any()) } returns flowOf(PagingData.empty())
        viewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `when viewmodel is created then initial state is Success with empty search input`() {
        val viewModel = CharactersViewModel(getCharactersUseCase)
        val state = viewModel.state.value

        assertTrue(state is CharactersUiContract.State.Success)
        assertEquals("", (state as CharactersUiContract.State.Success).searchInput)
    }

    @Test
    fun `on input changed updates search input in state`() = runTest {
        val initialState = viewModel.state.value as CharactersUiContract.State.Success
        viewModel.handleEvent(CharactersUiContract.Event.OnInputChanged("Rick"))

        val updatedState = viewModel.state.value as CharactersUiContract.State.Success
        assertEquals("Rick", updatedState.searchInput)
        assertEquals(initialState.selectedSortOrder, updatedState.selectedSortOrder)
    }

    @Test
    fun `on item clicked sends NavigateToDetails effect`() = runTest {
        val character = Character(
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
            episodes = listOf()
        )

        val effectFlow = viewModel.effect

        val job = launch {
            effectFlow.collect { effect ->
                assertTrue(effect is CharactersUiContract.Effect.NavigateToDetails)
                assertEquals(
                    2,
                    (effect as CharactersUiContract.Effect.NavigateToDetails).characterId
                )
            }
        }

        viewModel.handleEvent(CharactersUiContract.Event.OnItemClicked(character))
        advanceUntilIdle()
        job.cancel()
    }
}