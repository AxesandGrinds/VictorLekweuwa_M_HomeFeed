package com.vl.victorlekweuwamoody.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
* This is the response from the server.
* */
@Serializable
@Entity("home")
data class HomeResponse (

    @PrimaryKey(autoGenerate = true) val id: Int,

    @SerializedName("page")
    var page: Page? = Page()

)