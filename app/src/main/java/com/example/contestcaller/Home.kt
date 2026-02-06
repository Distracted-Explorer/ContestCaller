package com.example.contestcaller

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

//basic home view basicaly lazy column view
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val contests by viewModel.contests.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    when {
        loading -> Text("Loading...")
        error != null -> Text("Error: $error")
        else -> {
            LazyColumn {
                items(contests) { contest ->
                    Text(text = contest.name)
                }
            }
        }
    }
}

