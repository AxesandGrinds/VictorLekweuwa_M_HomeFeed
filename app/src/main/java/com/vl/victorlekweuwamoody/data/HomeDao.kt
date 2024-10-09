package com.vl.victorlekweuwamoody.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vl.victorlekweuwamoody.api.models.HomeResponse
import kotlinx.coroutines.flow.Flow

/*
* The Dao allows for saving the data locally to the app so that when next loading the data,
* it loads the cache first in case there is no internet access.
*/
@Dao
interface HomeDao {

    @Query("SELECT * FROM home")
    fun getHomeFeed(): Flow<HomeResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeFeed(homeResponse: HomeResponse)

    @Query("DELETE FROM home")
    suspend fun deleteHomeFeed()

}