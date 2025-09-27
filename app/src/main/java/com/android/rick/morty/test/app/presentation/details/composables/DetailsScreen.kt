package com.android.rick.morty.test.app.presentation.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Origin
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme

@Composable
fun DetailsScreen(
    character: Character
) {
    Scaffold(
        topBar = {
            DetailsTopBar(modifier = Modifier.padding(horizontal = RickAndMortyTheme.space.large))
        }
    ) {
        DetailsScreenContent(
            character = character,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun DetailsScreenContent(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium)
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = "Character image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.fallback_img),
            error = painterResource(R.drawable.fallback_img)
        )
        Text(
            text = character.name,
            style = RickAndMortyTheme.txtStyle.titleSmall
        )
        Text(
            text = character.creationDate,
            style = RickAndMortyTheme.txtStyle.bodyNormal
        )
        CharacterDetailsTabRow(
            character = character
        )
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreen(
        character = Character(
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
}