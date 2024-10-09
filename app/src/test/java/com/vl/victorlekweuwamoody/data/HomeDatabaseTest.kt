package com.vl.victorlekweuwamoody.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
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
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/*
* This unit tests tests that the Room Database is allowing insertion and deletion of Home Feed data.
*/
@RunWith(RobolectricTestRunner::class)
class HomeDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeDao: HomeDao
    private lateinit var db: HomeDatabase
    private lateinit var homeData: HomeResponse

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create a new home feed database using Room's in memory database
        db = Room.inMemoryDatabaseBuilder(
            context, HomeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        homeDao = db.homeDao()

        // This is an example Home Feed Data Response from Network Call
        homeData = HomeResponse(
            id=7, page= Page(
                cards= arrayListOf(
                    Cards(
                        cardType="text",
                        card= Card(value="Hello, Welcome to App!", attributes=Attributes(textColor="#262626", font=Font(size=30)),
                            image=Image(url=null, size=Size(width=null, height=null)), title=Title(value=null,
                                attributes=Attributes(textColor=null, font=Font(size=null))), description= Description(value=null,
                                attributes=Attributes(textColor=null, font=Font(size=null)))
                        )), Cards(cardType="title_description", card= Card(value=null, attributes= Attributes(textColor=null,
                        font= Font(size=null)), image= Image(url=null,
                        size= Size(width=null, height=null)), title= Title(value="Check out our App every week for exciting offers.",
                        attributes=Attributes(textColor="#262626", font=Font(size=24))), description=Description(value="Offers available every week!",
                        attributes=Attributes(textColor="#262626", font=Font(size=18))))), Cards(cardType="image_title_description",
                        card=Card(value=null, attributes=Attributes(textColor=null, font=Font(size=null)),
                            image=Image(url="https://qaevolution.blob.core.windows.net/assets/ios/3x/Featured@4.76x.png",
                                size=Size(width=1170, height=1498)), title=Title(value="Movie ticket to Dark Phoenix!",
                                attributes=Attributes(textColor="#FFFFFF", font=Font(size=18))),
                            description=Description(value="Tap to see offer dates and rescriptions.",
                                attributes=Attributes(textColor="#FFFFFF", font=Font(size=12))))),
                    Cards(cardType="title_description", card=Card(value=null, attributes=Attributes(textColor=null, font=Font(size=null)),
                        image=Image(url=null, size=Size(width=null, height=null)), title=Title(value="This is a sample text title v1",
                            attributes=Attributes(textColor="#262626", font=Font(size=20))),
                        description=Description(value="This is a sample text description v1",
                            attributes=Attributes(textColor="#262626", font=Font(size=12))))),
                    Cards(cardType="image_title_description", card=Card(value=null, attributes=Attributes(textColor=null, font=Font(size=null)),
                        image=Image(url="https://qaevolution.blob.core.windows.net/assets/ios/3x/Tuesday2@4.76x.png",
                            size=Size(width=1170, height=1170)), title=Title(value="25% off merch at t-mobile.com", attributes=Attributes(textColor="#FFFFFF",
                            font=Font(size=20))), description=Description(value="Tap to see offer dates and rescriptions.",
                            attributes=Attributes(textColor="#FFFFFF", font=Font(size=13))))), Cards(cardType="title_description",
                        card=Card(value=null, attributes=Attributes(textColor=null, font=Font(size=null)), image=Image(url=null, size=Size(width=null, height=null)),
                            title=Title(value="This is a sample text title v2",
                                attributes=Attributes(textColor="#262626", font=Font(size=20))), description=Description(value="This is a sample text description v2",
                                attributes=Attributes(textColor="#262626", font=Font(size=14))))))))
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close() // Close room database to delete all entries
    }

    @Test
    fun write_and_read_home_response_from_room() = runTest {
        homeDao.insertHomeFeed(homeData) // Add the home feed data
        homeDao.getHomeFeed().test{ // Check first emission of home feed data
            val emission = awaitItem()  // Get the home deed data
            // Check second emission contains the correct card_type
            assertEquals(emission.page?.cards?.get(1)?.cardType ?: "", "title_description")
            cancelAndIgnoreRemainingEvents() // Cancel the flow after testing
        }
    }

    @Test
    fun write_and_delete_home_response_from_room() = runTest {
        homeDao.insertHomeFeed(homeData) // Add the home feed data
        homeDao.deleteHomeFeed() // Delete the home feed data
        homeDao.getHomeFeed().test{ // Check first emission of home feed data
            val emission = awaitItem() // Get the home feed data
            assertEquals(emission, null) // Check first emission should not exist
            cancelAndIgnoreRemainingEvents() // Cancel the flow after testing
        }
    }

}