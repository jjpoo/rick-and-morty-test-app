package com.android.rick.morty.test.app.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.android.rick.morty.test.app.core.CHARACTER_ID
import com.android.rick.morty.test.app.presentation.characters.CharactersViewModel
import com.android.rick.morty.test.app.presentation.details.DetailsViewModel
import com.android.rick.morty.test.app.presentation.navigation.MainNavHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterId = intent.getIntExtra(CHARACTER_ID, 0)

        setContent {
            val charactersState by charactersViewModel.state
            val detailsState by detailsViewModel.state

            LaunchedEffect(characterId) {
                detailsViewModel.loadCharacter(characterId)
            }
            MainNavHos(
                characters = charactersViewModel.charactersFlow,
                charactersState = charactersState,
                charactersEvent = charactersViewModel::handleEvent,
                charactersEffect = charactersViewModel.effect,
                detailsState = detailsState,
                detailsEvent = detailsViewModel::handleDetailsEvent,
                detailsEffect = detailsViewModel.effect,
                getCharacterId = { id ->
                    detailsViewModel.loadCharacter(id)
                }
            )
        }
    }
}