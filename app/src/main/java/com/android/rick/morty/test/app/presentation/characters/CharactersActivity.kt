package com.android.rick.morty.test.app.presentation.characters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.android.rick.morty.test.app.core.CHARACTER_ID
import com.android.rick.morty.test.app.presentation.characters.composables.CharactersScreen
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import com.android.rick.morty.test.app.presentation.core.UiContract
import com.android.rick.morty.test.app.presentation.details.DetailsActivity
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RinkAndMortyAppTheme {
                val charactersScreenState by charactersViewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    charactersViewModel.getNavigationFlow().collect { navigationEffect ->
                        handleEffect(
                            effect = navigationEffect
                        )
                    }
                }
                CharactersScreen(
                    characters = charactersViewModel.charactersFlow,
                    state = charactersScreenState,
                    event = charactersViewModel::handleCharactersEvent
                )
            }
        }
    }

    private fun handleEffect(
        effect: UiContract.Effect
    ) {
        when (effect) {
            is CharactersUiContract.Effect.NavigateToDetails -> {
                val intent = Intent(this, DetailsActivity::class.java).apply {
                    putExtra(CHARACTER_ID, effect.characterId)
                }
                startActivity(intent)
            }

            is CharactersUiContract.Effect.ShowErrorToast -> {
                Toast.makeText(this, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

