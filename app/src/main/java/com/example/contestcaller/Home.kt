package com.example.contestcaller

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

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
                .padding( innerPadding)
        ){
            when {
                loading -> Text("Loading...")
                error != null -> Text("Error: $error")
                else -> {
                    CardPager(
                        cards = contests,
                        modifier = Modifier.fillMaxSize()
                    );
                }
            }
        }
    }
}


@Composable
fun CardPager(cards: List<StoredContestData>,modifier: Modifier){
    val pagerState = rememberPagerState(pageCount = { cards.size })

    HorizontalPager(
        modifier= modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 50.dp),
        pageSpacing = 16.dp
    ) { page ->

        val pageOffset = (
                (pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction
                ).absoluteValue

        val scale = lerp(
            0.85f,
            1f,
            1f - pageOffset.coerceIn(0f, 1f)
        )

        CardItem(
            contest = cards[page],
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        )
    }
}


@Composable
fun CardItem(
    contest : StoredContestData,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
                verticalArrangement = Arrangement.Center
        ){
            Row(
                Modifier.padding(15.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.code_forces),
                        contentDescription = "codeforces",
                    )
                    Text(contest.id.toString())
                    Text(contest.name, modifier = Modifier.padding(end = 50.dp))
                }
                Row(
                    Modifier.padding(15.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(contest.startTime)
                    if(contest.startTimeSeconds*1000 <= System.currentTimeMillis())
                        timeRemainingInContest(contest.durationSeconds*1000,contest.startTimeSeconds)
                    else Text(contest.duration)
                }
//                Row(
//                    Modifier.padding(15.dp).fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    Text(System.currentTimeMillis().toString())
////                    timeRemainingInContest(contest.durationSeconds,contest.startTimeSeconds)
//                }
//                Row(
//                    Modifier.padding(15.dp).fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    Text((contest.startTimeSeconds*1000).toString())
////                    timeRemainingInContest(contest.durationSeconds,contest.startTimeSeconds)
//                }
            }
        }
    }
}

@Composable
fun timeRemainingInContest(durationOfTime : Long,contestStartTime : Long){
    val startTimeMillis = contestStartTime * 1000
    val now = System.currentTimeMillis()
    var remainingTime by remember { mutableStateOf(durationOfTime) }

    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = System.currentTimeMillis()
            val elapsed = currentTime - startTimeMillis
            val newRemaining = durationOfTime - elapsed

            if (newRemaining <= 0) {
                remainingTime = 0
                break
            }
            remainingTime = newRemaining/1000
            delay(1000)
        }
    }

    Text(
        text = convertDurationToTime(remainingTime)
    )
}



@Preview(
    name="Dark Mode",
    showBackground = true,
)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
    CardPager(tempList, modifier = Modifier.padding(40.dp))
}