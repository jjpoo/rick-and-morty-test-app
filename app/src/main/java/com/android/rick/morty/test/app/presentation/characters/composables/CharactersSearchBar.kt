package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@Composable
fun CharactersSearchBar(
    input: String,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = input,
        onValueChange = { userInput ->
            onInputChanged(userInput)
        },
        placeholder = { Text(stringResource(R.string.search_character)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        shape = RoundedCornerShape(RickAndMortyTheme.space.small),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun CharactersSearchBarPreview() {
    RinkAndMortyAppTheme {
        CharactersSearchBar(
            input = "J",
            onInputChanged = {}
        )
    }
}