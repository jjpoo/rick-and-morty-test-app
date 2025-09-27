package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Origin
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@Composable
fun CharactersScreen(
    filteredCharacters: List<Character>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CharactersTopBar(modifier = Modifier.padding(horizontal = RickAndMortyTheme.space.large))
        },
        content = {
            CharactersScreenContent(
                characters = filteredCharacters,
                modifier = Modifier.padding(it),
                onQueryChanged = {}
            )
        }
    )
}

@Composable
fun CharactersScreenContent(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }

    val displayedCharacters = if (query.isEmpty()) {
        characters
    } else {
        characters.filter { it.name.contains(query, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = RickAndMortyTheme.space.medium),
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        CharactersSearchBar(
            query = query,
            onQueryChange = { onQueryChanged(it) },
            onSearch = { }
        )
        CharacterList(
            characters = displayedCharacters
        )
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharactersItem(
                character = character
            )
        }
    }
}

@Preview
@Composable
fun CharacterListPreview() {
    RinkAndMortyAppTheme {
        CharactersScreen(
            filteredCharacters = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    image = "",
                    status = Status.ALIVE,
                    species = Species.HUMAN,
                    gender = Gender.MALE,
                    origin = Origin(name = "Earth", url = ""),
                    location = "",
                    creationDate = "11/4/2017",
                    episodes = listOf()
                ),
                Character(
                    id = 2,
                    name = "Rick Sanchez",
                    image = "",
                    status = Status.ALIVE,
                    species = Species.HUMAN,
                    gender = Gender.MALE,
                    origin = Origin(name = "Earth", url = ""),
                    location = "",
                    creationDate = "11/4/2017",
                    episodes = listOf()
                )
            )
        )
    }
}
