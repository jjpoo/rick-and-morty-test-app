package com.android.rick.morty.test.app.presentation.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme

@Composable
fun DetailsTopBar(
    character: Character,
    isFavourite: Boolean,
    onBackClicked: () -> Unit,
    onFavClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackClicked() },
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Icon back"
            )
        }
        Text(
            text = "Details",
            style = RickAndMortyTheme.txtStyle.titleLarge
        )
        IconButton(
            onClick = { onFavClicked(character.id) },
        ) {
            Icon(
                imageVector = if (isFavourite)
                    Icons.Filled.Favorite
                else
                    Icons.Outlined.FavoriteBorder,
                contentDescription = "Icon favorite"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsTopBarPreview() {
    DetailsTopBar(
        character = Character(
            id = 2,
            name = "Rick Sanchez",
            image = "",
            status = Status.ALIVE,
            species = Species.HUMAN,
            gender = Gender.MALE,
            type = "",
            origin = "Earth",
            location = "",
            created = "11/4/2017",
            episodes = listOf()
        ),
        true, {}, { _ -> }, Modifier,
    )
}