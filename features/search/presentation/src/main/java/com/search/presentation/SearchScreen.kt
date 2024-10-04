package com.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var items = remember {
        mutableStateListOf<String>()
    }

    val allMovies = listOf(
        "Spider-Man: No Way Home",
        "Spider-Man: Into the Spider-Verse",
        "Spider-Man 2",
        "Spider-Man: Homecoming",
        "Spider-Man: Far From Home",
        "The Amazing Spider-Man",
        "Spider-Man 3"
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchBar(query = text, onQueryChange = {
                text = it
            }, onSearch = {
                items.add(text)
                active = false
            }, active = active, onActiveChange = {
                active = it
            }, leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            },
                trailingIcon = {
                    if (active) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()) {
                                    text = ""
                                } else {
                                    active = false
                                }
                            })
                    }
                },
                placeholder = { Text(text = "Buscar") }, modifier = Modifier.padding(padding)
            ) {
                items.forEach { item ->
                    Row(modifier = Modifier.padding(14.dp)) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(text = item)
                    }
                }
            }

            if (allMovies.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(allMovies) {
                        Text(text = it)
                    }
                }
            }
        }

    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}