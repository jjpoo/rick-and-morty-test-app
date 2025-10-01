package com.android.rick.morty.test.app.presentation.details.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@Composable
fun CharacterDetailsTabRow(
    character: Character,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Info", "Episodes")

    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> InformationTab(character)
            1 -> EpisodesTab(character.episodes)
        }
    }
}

@Composable
fun InformationTab(
    character: Character,
    modifier: Modifier = Modifier
) {
    val texts = remember(character) {
        listOf(
            character.status.name,
            character.species.name,
            character.gender.name
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium),
        modifier = modifier.padding(vertical = RickAndMortyTheme.space.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            texts.forEach { text ->
                Text(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(RickAndMortyTheme.space.small),
                    text = text,
                    style = RickAndMortyTheme.txtStyle.titleSmall,
                    color = Color.Red
                )
            }
        }
        LocationOriginInfo(
            title = "Origin",
            content = character.origin
        )
        LocationOriginInfo(
            title = "Location",
            content = character.location
        )
    }
}

@Composable
fun LocationOriginInfo(
    title: String,
    content: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.small)
    ) {
        Text(
            text = title,
            style = RickAndMortyTheme.txtStyle.titleSmall
        )
        Text(
            text = content,
            style = RickAndMortyTheme.txtStyle.bodyNormal
        )
    }
}

@Composable
fun EpisodesTab(episodes: List<String>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        items(episodes) { episode ->
            Text(
                text = episode,
                style = RickAndMortyTheme.txtStyle.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailsTabRowPreview() {
    RinkAndMortyAppTheme {
        CharacterDetailsTabRow(
            character = Character(
                id = 2,
                name = "Rick Sanchez",
                image = "",
                status = Status.ALIVE,
                species = Species.HUMAN,
                gender = Gender.MALE,
                type = "",
                origin = "Earth",
                location = "Rick Land",
                created = "11/4/2017",
                episodes = listOf(),
                isFavorite = false
            )
        )
    }
}