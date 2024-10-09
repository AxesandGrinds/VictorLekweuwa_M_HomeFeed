package com.vl.victorlekweuwamoody.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.vl.victorlekweuwamoody.api.models.Cards
import com.vl.victorlekweuwamoody.util.HomeState

/*
* This screen, the Home Screen. is the first screen the user sees after the splash screen.
* This screen contains the UI associated with the the API response.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    /*
    * state defined in the view model holds the state of the network request.
    * */
    val state = homeViewModel.state.observeAsState().value

    /*
    * homeResponse defined in the view model holds the data from the network request.
    * */
    val homeResponse = homeViewModel.homeData.observeAsState().value

    /*
    * networkErrorMessage defined in the view model holds the type of error from the network request.
    * */
    val networkErrorMessage = homeViewModel.networkErrorMessage.observeAsState().value

    // We set this up just in case if the future programmer needs to add a snack bar message.
    // Initially it was considered to show a snack bar when their is a server error but I change my mind.
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    /*
    * We make this network call from the view model in a LaunchedEffect
    * so that this code is called exactly once no matter how many times the UI recomposes.
    * */
    LaunchedEffect(Unit) {
        homeViewModel.getHomeFeed()
    }

    Scaffold(

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        "V.L -Home Feed App",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp),
                        overflow = TextOverflow.Ellipsis
                    )
                },

                )
        },
        content = { innerPadding ->

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                /*
                The view model provide state of the app.
                State 1 - Initial state - Nothing needs to be done. Loading.
                State 2 - Loading State - The view model is currently doing a network call.
                State 3 - Error State - Show an error message from the server.
                State 4 - Home Feed Data Successful State - The information loaded from the server.
                */
                when (state) {

                    HomeState.Initial -> {
                        // Initialized State 0 - Progress Dialog Circling
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp).testTag("CircularProgressIndicator Loading"),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                    HomeState.Loading -> {
                        // Loading State 1 - Progress Dialog Circling
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                    /*
                    * Getting the server error message from the view model and showing the user.
                    * */
                    HomeState.Error -> {
                        // Error State 2 -Show Error
                        Text(
                            "Error. Please Try Again Later. $networkErrorMessage",
                            style = TextStyle(fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red)
                        )
                    }
                    HomeState.Success -> {

                        /*
                         Home Feed Data Successful State 3 - Show Info
                        */
                        ElevatedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .padding(0.dp)
                        ) {

                            /*
                            * This lazy column contains the recyclerview code that shows the right card
                            * depending on the cart_type from the server response.
                            * */
                            LazyColumn {

                                println("ATTENTION ATTENTION: Home Feed Data ${homeResponse?.page?.cards!!}")

                                    items(homeResponse.page?.cards!!) { cards ->

                                        when (cards.cardType) {
                                            "text" -> {
                                                TextOnlyCard(cards)
                                            }
                                            "title_description" -> {
                                                TitleDescriptionCard(cards)
                                            }
                                            "image_title_description" -> {
                                                ImageTitleDescriptionCard(cards)
                                            }
                                            else -> {
                                                // Handle other card types
                                                Text(
                                                    "Unknown Card Type: ${cards.cardType}",
                                                    style = TextStyle(
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Red
                                                    )
                                                )
                                            }
                                        }

                                    }

                            }

                        }

                    }
                    else -> {
                        // Error State 2 -Show Error
                        Text(
                            "Error. Please Try Again Later.",
                            style = TextStyle(fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red)
                        )
                    }

                }

            }

        })

}

fun hexToColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

/*
This Composable shows the title section from the API response
for title_description or image_title_description card type.
*/
@Composable
fun TitleText(cards: Cards) {

    val titleColorLongValue = cards.card?.title?.attributes?.textColor
    val titleTextColor = hexToColor(titleColorLongValue!!)
    val titleFontSize = cards.card?.title?.attributes?.font?.size?.sp
    val titleTextValue = cards.card?.title?.value

    println("ATTENTION ATTENTION: TitleText Composable Color: ${titleTextColor.toString()}")

    Text(text = titleTextValue!!,
        overflow = TextOverflow.Visible,
        style = TextStyle(
            color = titleTextColor,
            fontSize = titleFontSize!!,
            fontWeight = FontWeight.Bold

        )
    )

}

/*
This Composable shows the description section from the API response
for title_description or image_title_description card type.
*/
@Composable
fun DescriptionText(cards: Cards) {

    val desColorLongValue = cards.card?.description?.attributes?.textColor
    val desTextColor = hexToColor(desColorLongValue!!)
    val desFontSize = cards.card?.description?.attributes?.font?.size?.sp
    val desTextValue = cards.card?.description?.value

    println("ATTENTION ATTENTION: DescriptionText Composable Color: ${desTextColor.toString()}")

    Text(text = desTextValue!!,
        overflow = TextOverflow.Visible,
        style = TextStyle(
            color = desTextColor,
            fontSize = desFontSize!!,
            fontWeight = FontWeight.Normal
        )
    )

}

/*
This Composable shows the text section from the API response's text only card type.
*/
@Composable
fun TextOnlyCard(cards: Cards) {

    val textOnlyColorLongValue = cards.card?.attributes?.textColor
    val textOnlyTextColor = hexToColor(textOnlyColorLongValue!!)
    val textOnlyFontSize = cards.card?.attributes?.font?.size?.sp
    val textOnlyTextValue = cards.card?.value

    println("ATTENTION ATTENTION: TextOnlyCard Composable Color: ${textOnlyTextColor.toString()}")

    Text(
        text = textOnlyTextValue!!,
        overflow = TextOverflow.Visible,
        style = TextStyle(
            color = textOnlyTextColor,
            fontSize = textOnlyFontSize!!,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(10.dp)
    )

}

/*
This Composable shows the entire card from the API response
for title_description  card type.
*/
@Composable
fun TitleDescriptionCard(cards: Cards) {

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        TitleText(cards)
        DescriptionText(cards)
    }

}

/*
This Composable shows the entire card from the API response
for image_title_description card type.
*/
@Composable
fun ImageTitleDescriptionCard(cards: Cards) {

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    val imageUrl = cards.card?.image?.url

    // Using the image height and image width provides by the API makes the image too large
    // on a mobile phone.
    val imageHeight = cards.card?.image?.size?.height?.dp
    val imageWidth = cards.card?.image?.size?.width?.dp

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Using the image height and image width provides by the API makes the image too large
        // on a mobile phone.
        AsyncImage(
            model = imageUrl,
            imageLoader = imageLoader,
            contentDescription = "Image from URL ${imageUrl}",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
//                .height(imageHeight)
//                .width(imageWidth)
        )

        Column(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 20.dp)
                .align(Alignment.BottomStart)
        ) {
            TitleText(cards)
            DescriptionText(cards)
        }

    }

}