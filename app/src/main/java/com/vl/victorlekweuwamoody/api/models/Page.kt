package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Page (

    @SerializedName("cards")
    var cards: ArrayList<Cards> = arrayListOf()

)