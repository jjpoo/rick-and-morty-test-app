package com.android.rick.morty.test.app.presentation.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme

@Composable
fun DetailsTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = RickAndMortyTheme.space.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Icon back"
        )
        Text(
            text = "Details",
            style = RickAndMortyTheme.txtStyle.titleLarge
        )
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Icon filter"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsTopBarPreview() {
    DetailsTopBar()
}