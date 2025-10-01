package com.android.rick.morty.test.app.presentation.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.presentation.state.RickAndMortyUiContract
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme

@Composable
fun DetailsScreen(
//    detailsState: RickAndMortyUiContract.DetailsScreenState,
//    event: (RickAndMortyUiContract.ScreenEvent) -> Unit
) {
//    when (detailsState) {
//        is RickAndMortyUiContract.DetailsScreenState.Loading -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//
//        is RickAndMortyUiContract.DetailsScreenState.Success -> {
//            DetailsScreenContent(
//                character = detailsState.character,
//                event = event
//            )
//        }
//
//        is RickAndMortyUiContract.DetailsScreenState.Error -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = detailsState.message ?: stringResource(R.string.unknown_error),
//                    style = RickAndMortyTheme.txtStyle.bodyNormal
//                )
//            }
//        }
//    }
}

@Composable
private fun DetailsScreenContent(
    character: Character,
    event: (RickAndMortyUiContract.ScreenEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            DetailsTopBar(
                character = character,
                onBackClicked = {
                    event(RickAndMortyUiContract.DetailsScreenEvents.OnBackClicked)
                },
                onFavClicked = { id, state ->
                    event(RickAndMortyUiContract.DetailsScreenEvents.OnFavClicked(id, state))
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
                style = RickAndMortyTheme.txtStyle.titleSmall
            )
            Text(
                text = character.created,
                style = RickAndMortyTheme.txtStyle.bodyNormal
            )
            CharacterDetailsTabRow(
                character = character
            )
        }
    }
}

//@Preview
//@Composable
//private fun DetailsScreenPreview() {
//    DetailsScreen(
////        detailsState = RickAndMortyUiContract.DetailsScreenState.Success(
////            character = Character(
////                id = 2,
////                name = "Rick Sanchez",
////                image = "",
////                status = Status.ALIVE,
////                species = Species.HUMAN,
////                gender = Gender.MALE,
////                type = "",
////                origin = "Earth",
////                location = "",
////                created = "11/4/2017",
////                episodes = listOf(),
////                isFavorite = false
////            )
////        ),
////        event = {}
////    )
//}

//@Preview
//@Composable
//private fun DetailsScreenLoadingPreview() {
//    DetailsScreen(
//        detailsState = RickAndMortyUiContract.DetailsScreenState.Loading,
//        event = {}
//    )
//}