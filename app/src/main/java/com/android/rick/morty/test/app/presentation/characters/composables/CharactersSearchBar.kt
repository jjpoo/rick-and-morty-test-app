package com.android.rick.morty.test.app.presentation.characters.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RickAndMortyTheme
import com.android.rick.morty.test.app.presentation.ui.theme.custom.RinkAndMortyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(RickAndMortyTheme.space.small),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    onSearch(query)
                    expanded = false
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text("Search character") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon"
                    )
                }
            )
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun CharactersSearchBarPreview() {
    RinkAndMortyAppTheme {
        CharactersSearchBar(
            query = "J",
            onQueryChange = {},
            onSearch = {}
        )
    }
}