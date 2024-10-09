package com.vl.victorlekweuwamoody.features.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.vl.victorlekweuwamoody.api.models.Attributes
import com.vl.victorlekweuwamoody.api.models.Card
import com.vl.victorlekweuwamoody.api.models.Cards
import com.vl.victorlekweuwamoody.api.models.Description
import com.vl.victorlekweuwamoody.api.models.Font
import com.vl.victorlekweuwamoody.api.models.HomeResponse
import com.vl.victorlekweuwamoody.api.models.Image
import com.vl.victorlekweuwamoody.api.models.Page
import com.vl.victorlekweuwamoody.api.models.Size
import com.vl.victorlekweuwamoody.api.models.Title
import com.vl.victorlekweuwamoody.data.HomeRepository
import com.vl.victorlekweuwamoody.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
//import org.mockito.junit.MockitoJUnitRunner

/*
* This test is failing because the Android API 34 is the target api.
* There is an error that says
* java.lang.RuntimeException: java.lang.SecurityException: Writable dex file '....jar' is not allowed.
* This error seems to not have been solved thereby prevent testing on API 34.
* Furthermore, decreasing the target api to 33 throws other errors with Jetpack Compose
* */
//@RunWith(MockitoJUnitRunner::class)
class HomeScreenKtTest {

    /*
    * We are loading a composable screen so we should use this rule.
    * */
    @get:Rule
    val compose = createComposeRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeData: HomeResponse

    val mockkHomeRepository = mockk<HomeRepository>(relaxed = true)

    /*
    Adding example server response to homeData variable as set up for test.
    */
    @Before
    fun setUp() {

        // This is an example Home Feed Data Response from Network Call
        homeData = HomeResponse(
            id=7, page= Page(
                cards= arrayListOf(
                    Cards(
                        cardType="text",
                        card= Card(value="Hello, Welcome to App!", attributes= Attributes(textColor="#262626", font= Font(size=30)),
                            image= Image(url=null, size= Size(width=null, height=null)), title= Title(value=null,
                                attributes= Attributes(textColor=null, font= Font(size=null))
                            ), description= Description(value=null,
                                attributes= Attributes(textColor=null, font= Font(size=null))
                            )
                        )
                    ), Cards(cardType="title_description", card= Card(value=null, attributes= Attributes(textColor=null,
                        font= Font(size=null)
                    ), image= Image(url=null,
                        size= Size(width=null, height=null)
                    ), title= Title(value="Check out our App every week for exciting offers.",
                        attributes= Attributes(textColor="#262626", font= Font(size=24))
                    ), description= Description(value="Offers available every week!",
                        attributes= Attributes(textColor="#262626", font= Font(size=18))
                    )
                    )
                    ), Cards(cardType="image_title_description",
                        card= Card(value=null, attributes= Attributes(textColor=null, font= Font(size=null)),
                            image= Image(url="https://qaevolution.blob.core.windows.net/assets/ios/3x/Featured@4.76x.png",
                                size= Size(width=1170, height=1498)
                            ), title= Title(value="Movie ticket to Dark Phoenix!",
                                attributes= Attributes(textColor="#FFFFFF", font= Font(size=18))
                            ),
                            description= Description(value="Tap to see offer dates and rescriptions.",
                                attributes= Attributes(textColor="#FFFFFF", font= Font(size=12))
                            )
                        )
                    ),
                    Cards(cardType="title_description", card= Card(value=null, attributes= Attributes(textColor=null, font= Font(size=null)),
                        image= Image(url=null, size= Size(width=null, height=null)), title= Title(value="This is a sample text title v1",
                            attributes= Attributes(textColor="#262626", font= Font(size=20))
                        ),
                        description= Description(value="This is a sample text description v1",
                            attributes= Attributes(textColor="#262626", font= Font(size=12))
                        )
                    )
                    ),
                    Cards(cardType="image_title_description", card= Card(value=null, attributes= Attributes(textColor=null, font= Font(size=null)),
                        image= Image(url="https://qaevolution.blob.core.windows.net/assets/ios/3x/Tuesday2@4.76x.png",
                            size= Size(width=1170, height=1170)
                        ), title= Title(value="25% off merch at t-mobile.com", attributes= Attributes(textColor="#FFFFFF",
                            font= Font(size=20)
                        )
                        ), description= Description(value="Tap to see offer dates and rescriptions.",
                            attributes= Attributes(textColor="#FFFFFF", font= Font(size=13))
                        )
                    )
                    ), Cards(cardType="title_description",
                        card= Card(value=null, attributes= Attributes(textColor=null, font= Font(size=null)), image= Image(url=null, size= Size(width=null, height=null)),
                            title= Title(value="This is a sample text title v2",
                                attributes= Attributes(textColor="#262626", font= Font(size=20))
                            ), description= Description(value="This is a sample text description v2",
                                attributes= Attributes(textColor="#262626", font= Font(size=14))
                            )
                        )
                    )
                ))
        )
    }

    /*
    * Progress bar should open as soon as you load the app.
    * */
    @Test
    fun test_Home_Screen_Progress_Indicator() {

        homeViewModel = HomeViewModel(mockkHomeRepository)

        compose.setContent {
            HomeScreen(homeViewModel)
        }

        runBlocking(Dispatchers.Main) {
            homeViewModel.getHomeFeed()
            compose.onNodeWithTag("CircularProgressIndicator Loading").assertIsDisplayed()
        }
    }

    /*
    * When mock data is loaded, the progress indicator should disappear
    * */
    @Test
    fun test_Home_Screen() {

        every {mockkHomeRepository.getHomeFeed()} returns flow {
            emit(Resource.Success(homeData))
        }

        homeViewModel = HomeViewModel(mockkHomeRepository)

        compose.setContent {
            HomeScreen(homeViewModel)
        }

        runBlocking(Dispatchers.Main) {
            homeViewModel.getHomeFeed()
            compose.awaitIdle()
            compose.onNodeWithTag("CircularProgressIndicator Loading").assertIsNotDisplayed()
        }
    }

}