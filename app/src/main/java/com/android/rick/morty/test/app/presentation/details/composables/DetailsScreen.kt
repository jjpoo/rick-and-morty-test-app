package com.android.rick.morty.test.app.presentation.details.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.details.state.DetailsUiContract
import com.android.rick.morty.test.app.presentation.details.utils.getCreationDate
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    state: DetailsUiContract.State,
    event: (DetailsUiContract.Event) -> Unit
) {
    RinkAndMortyAppTheme {
        when (state) {
            is DetailsUiContract.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailsUiContract.State.Success -> {
                state.character?.let {
                    DetailsScreenContent(
                        character = it,
                        isFavourite = state.isFavourite,
                        event = event
                    )
                }
            }

            is DetailsUiContract.State.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        style = RickAndMortyTheme.txtStyle.bodyNormal
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DetailsScreenContent(
    isFavourite: Boolean,
    character: Character,
    event: (DetailsUiContract.Event) -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            DetailsTopBar(
                character = character,
                isFavourite = isFavourite,
                onBackClicked = {
                    event(DetailsUiContract.Event.OnBackClicked)
                },
                onFavClicked = { id ->
                    event(DetailsUiContract.Event.OnFavClicked(id))
                },
                modifier = Modifier.padding(top = RickAndMortyTheme.space.large)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = RickAndMortyTheme.space.medium),
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
                style = RickAndMortyTheme.txtStyle.titleSmall,
                modifier = Modifier.padding(top = RickAndMortyTheme.space.large)
            )
            Text(
                text = character.created.getCreationDate(),
                style = RickAndMortyTheme.txtStyle.bodyNormal
            )
            CharacterDetailsTabRow(
                character = character
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DetailsScreenPreview() {
    RinkAndMortyAppTheme {
        DetailsScreen(
            state = DetailsUiContract.State.Success(
                character = Character(
                    id = 2,
                    name = "Rick Sanchez",
                    image = "",
                    status = Status.ALIVE,
                    species = Species.HUMAN,
                    gender = Gender.MALE,
                    type = "",
                    origin = "Earth",
                    location = "Earth",
                    created = "11/4/2017",
                    episodes = listOf()
                )
            ),
            event = {}
        )
    }
}
