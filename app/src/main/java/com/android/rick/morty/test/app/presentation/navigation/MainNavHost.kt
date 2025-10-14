package com.android.rick.morty.test.app.presentation.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.presentation.characters.composables.CharactersScreen
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import com.android.rick.morty.test.app.presentation.details.composables.DetailsScreen
import com.android.rick.morty.test.app.presentation.details.state.DetailsUiContract
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavHos(
    characters: Flow<PagingData<Character>>,
    charactersState: CharactersUiContract.State,
    charactersEvent: (CharactersUiContract.Event) -> Unit,
    charactersEffect: Flow<CharactersUiContract.Effect>,
    detailsState: DetailsUiContract.State,
    detailsEvent: (DetailsUiContract.Event) -> Unit,
    detailsEffect: Flow<DetailsUiContract.Effect>,
    getCharacterId: (Int) -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        charactersEffect.collect { effect ->
            when (effect) {
                is CharactersUiContract.Effect.NavigateToDetails -> {
                    navController.navigate(Screen.Details.createRoute(effect.characterId))
                }

                is CharactersUiContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        detailsEffect.collect { effect ->
            when (effect) {
                is DetailsUiContract.Effect.NavigateBack -> navController.popBackStack()

                is DetailsUiContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Characters.route
    ) {
        composable(route = Screen.Characters.route) {
            CharactersScreen(
                characters = characters,
                state = charactersState,
                event = charactersEvent
            )
        }
        composable(route = Screen.Details.route) { navBackStackEntry ->
            val characterId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            LaunchedEffect(characterId) {
                getCharacterId(characterId)
            }
            DetailsScreen(
                state = detailsState,
                event = detailsEvent
            )
        }
    }
}