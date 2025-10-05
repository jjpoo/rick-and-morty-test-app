package com.android.rick.morty.test.app.presentation.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.android.rick.morty.test.app.core.CHARACTER_ID
import com.android.rick.morty.test.app.presentation.characters.CharactersActivity
import com.android.rick.morty.test.app.presentation.details.composables.DetailsScreen
import com.android.rick.morty.test.app.presentation.details.state.DetailsUiContract
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterId = intent.getIntExtra(CHARACTER_ID, 0)

        setContent {
            val state by detailsViewModel.state.collectAsState()

            LaunchedEffect(characterId) {
                characterId.let {
                    detailsViewModel.loadCharacter(characterId)
                }
            }

            LaunchedEffect(Unit) {
                detailsViewModel.effect().collect { navigationEffect ->
                    handleNavigation(
                        effect = navigationEffect
                    )
                }
            }

            DetailsScreen(
                detailsState = state,
                event = detailsViewModel::handleDetailsEvent
            )
        }
    }

    private fun handleNavigation(
        effect: DetailsUiContract.Effect
    ) {
        when (effect) {
            is DetailsUiContract.Effect.NavigateBack -> {
                val intent = Intent(this@DetailsActivity, CharactersActivity::class.java)
                startActivity(intent)
            }

            is DetailsUiContract.Effect.ShowErrorToast -> {
                Toast.makeText(this, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}