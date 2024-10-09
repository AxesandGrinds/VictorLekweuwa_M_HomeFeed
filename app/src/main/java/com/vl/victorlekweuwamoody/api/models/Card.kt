package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Card (

    @SerializedName("value")
    var value: String? = null,

    @SerializedName("attributes")
    var attributes: Attributes? = Attributes(),

    @SerializedName("image")
    var image: Image? = Image(),

    @SerializedName("title")
    var title: Title? = Title(),

    @SerializedName("description")
    var description: Description? = Description()
)