package com.example.contestcaller

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

//basic home view basicaly lazy column view
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val contests by viewModel.contests.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
        topBar = {
            //row because it topAppBar has extra height because of mobiles height due to materail3
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = "Contest Caller",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = {
                    //todo to create page for account input
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource( R.drawable.accounts_icon ),
                        contentDescription = "Accounts",
                        tint = Color(0xFF0358EC),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
        //innerPadding indicates topbars height
    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            when {
                loading -> Text("Loading...")
                error != null -> Text("Error: $error")
                else -> {
                    LazyColumn (Modifier.padding(5.dp)){
                        items(contests) { contest ->
                            contestCard(contest)
                            Spacer(modifier = Modifier.padding(bottom = 5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun contestCard(contest: StoredContestData){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ){
        Row(
            Modifier.padding(15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(contest.id.toString())
            Text(contest.name, modifier = Modifier.padding(end = 50.dp))
        }
        Row(
            Modifier.padding(15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(contest.difficulty.toString()) //todo convert to stars
            Text(contest.startTimeSeconds.toString())
            Text(contest.durationSeconds.toString())
        }
    }
}

@Preview(
    name="Dark Mode",
    showBackground = true,
)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}


@Preview(showBackground = true)
@Composable
fun contestCardPreview(){
    contestCard(tempStoredData)
}


