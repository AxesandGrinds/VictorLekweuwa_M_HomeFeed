package com.vl.victorlekweuwamoody.data

import androidx.room.withTransaction
import com.vl.victorlekweuwamoody.api.HomeApi
import com.vl.victorlekweuwamoody.util.networkBoundResource
import javax.inject.Inject

/*
* This repository makes a network request and initially loads the local databae values
* before making the network request.
* */
class HomeRepository @Inject constructor(
    private val api: HomeApi,
    private val db: HomeDatabase
) {

    private val homeDao = db.homeDao()

    suspend fun deleteHomeFeed() {
        db.withTransaction {
            homeDao.deleteHomeFeed()
        }
    }

    fun getHomeFeed() = networkBoundResource(
        query = {
            homeDao.getHomeFeed()
        },
        fetch = {
            api.getHomeFeed()
        },
        saveFetchResult = { home ->
            db.withTransaction {
                homeDao.deleteHomeFeed()
                homeDao.insertHomeFeed(home)
            }
        }
    )

}