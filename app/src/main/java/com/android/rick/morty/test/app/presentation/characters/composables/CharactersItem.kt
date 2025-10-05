package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@Composable
fun CharactersItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = RickAndMortyTheme.space.medium),
        horizontalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = "Character image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(RickAndMortyTheme.space.extraLarge)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.fallback_img)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.small)
        ) {
            Text(
                text = character.name,
                style = RickAndMortyTheme.txtStyle.titleLarge
            )
            Text(
                text = character.species.name,
                style = RickAndMortyTheme.txtStyle.bodySmall
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterListItemPreview() {
    RinkAndMortyAppTheme {
        CharactersItem(
            character = Character(
                id = 1,
                name = "Rick Sanchez",
                image = "",
                status = Status.ALIVE,
                species = Species.HUMAN,
                gender = Gender.MALE,
                origin = "Earth",
                type = "",
                location = "",
                created = "11/4/2017",
                episodes = listOf()
            )
        )
    }
}