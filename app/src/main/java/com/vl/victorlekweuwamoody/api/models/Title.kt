package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Title (

    @SerializedName("value")
    var value: String? = null,

    @SerializedName("attributes")
    var attributes: Attributes? = Attributes()

)