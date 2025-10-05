package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.R
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@Composable
fun CharactersTopBar(
    sortOrder: SortOrder?,
    onSortClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Characters",
            style = RickAndMortyTheme.txtStyle.titleLarge
        )
        SortWidget(
            sortOrder = sortOrder,
            onSortClicked = onSortClicked
        )
    }
}

@Composable
private fun SortWidget(
    sortOrder: SortOrder?,
    onSortClicked: () -> Unit
) {
    val (text, color) = when (sortOrder) {
        SortOrder.ASC -> stringResource(R.string.sort_asc) to colorResource(R.color.sky_blue)
        SortOrder.DESC -> stringResource(R.string.sort_desc) to colorResource(R.color.light_yellow)
        null -> stringResource(R.string.sort_list) to Color.LightGray
    }

    Box(
        modifier = Modifier
            .background(color = color)
            .clickable { onSortClicked() }
    ) {
        Text(
            text = text,
            style = RickAndMortyTheme.txtStyle.titleSmall,
            color = Color.Black,
            modifier = Modifier.padding(RickAndMortyTheme.space.medium)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CharactersTopBarPreview() {
    RinkAndMortyAppTheme {
        CharactersTopBar(sortOrder = SortOrder.ASC, onSortClicked = {})
    }
}