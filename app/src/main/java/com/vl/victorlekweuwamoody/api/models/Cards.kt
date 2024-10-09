package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Cards (

    @SerializedName("card_type")
    var cardType: String,

    @SerializedName("card")
    var card: Card? = Card()

)