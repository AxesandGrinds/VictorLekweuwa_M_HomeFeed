package com.vl.victorlekweuwamoody.api

import com.vl.victorlekweuwamoody.api.models.HomeResponse
import retrofit2.http.GET

interface HomeApi {

    companion object {
        const val BASE_URL = "https://private-8ce77c-tmobiletest.apiary-mock.com/"
    }

    @GET("test/home")
    suspend fun getHomeFeed(): HomeResponse

}