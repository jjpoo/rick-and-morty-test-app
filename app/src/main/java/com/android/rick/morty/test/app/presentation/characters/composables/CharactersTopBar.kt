package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme

@Composable
fun CharactersTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = RickAndMortyTheme.space.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Characters",
            style = RickAndMortyTheme.txtStyle.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(RickAndMortyTheme.space.small)
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_filter_list_24),
                contentDescription = "Icon filter"
            )
//            Icon(
//                painter = painterResource(R.drawable.outline_sort_24),
//                contentDescription = "Icon sort"
//            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CharactersTopBarPreview() {
    CharactersTopBar()
}