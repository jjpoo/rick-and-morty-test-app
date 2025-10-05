package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CharactersScreen(
    characters: Flow<PagingData<Character>>,
    state: CharactersUiContract.State,
    event: (CharactersUiContract.Event) -> Unit
) {
    val lazyCharacters = characters.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.padding(horizontal = RickAndMortyTheme.space.medium),
        topBar = {
            if (state is CharactersUiContract.State.Success) {
                CharactersTopBar(
                    sortOrder = state.selectedSortOrder,
                    onSortClicked = {
                        event(CharactersUiContract.Event.OnSortCharactersClicked)
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }
        },
        content = { paddingValues ->
            when (state) {
                is CharactersUiContract.State.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CharactersUiContract.State.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = state.message ?: stringResource(R.string.unknown_error))
                    }
                }

                is CharactersUiContract.State.Success -> {
                    CharactersScreenContent(
                        characters = lazyCharacters,
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(top = 10.dp),
                        searchQuery = state.searchInput,
                        onCharacterClicked = {
                            event(CharactersUiContract.Event.OnItemClicked(it))
                        },
                        onQueryChanged = {
                            event(CharactersUiContract.Event.OnInputChanged(it))
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun CharactersScreenContent(
    characters: LazyPagingItems<Character>,
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onCharacterClicked: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredCharacters = remember(characters, searchQuery) {
        derivedStateOf {
            characters.itemSnapshotList
                .filterNotNull()
                .filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = RickAndMortyTheme.space.large),
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        CharactersSearchBar(
            input = searchQuery,
            onInputChanged = onQueryChanged
        )
        CharacterList(
            characters = filteredCharacters.value,
            onCharacterClicked = onCharacterClicked
        )
    }
}

@Composable
private fun CharacterList(
    characters: List<Character>,
    onCharacterClicked: (Character) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharactersItem(
                character = character,
                modifier = Modifier.clickable { onCharacterClicked(character) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CharacterListPreview() {
    RinkAndMortyAppTheme {
        CharacterList(
            characters = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    image = "",
                    status = Status.ALIVE,
                    species = Species.HUMAN,
                    gender = Gender.MALE,
                    origin = "Earth",
                    location = "",
                    created = "11/4/2017",
                    episodes = listOf(),
                    type = ""
                ),
                Character(
                    id = 2,
                    name = "Rick Sanchez",
                    image = "",
                    status = Status.ALIVE,
                    species = Species.HUMAN,
                    gender = Gender.MALE,
                    origin = "Earth",
                    location = "",
                    created = "11/4/2017",
                    episodes = listOf(),
                    type = ""
                )
            ),
            onCharacterClicked = {}
        )
    }
}
