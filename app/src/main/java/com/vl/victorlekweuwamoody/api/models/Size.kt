package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Size (

    @SerializedName("width")
    var width: Int? = null,

    @SerializedName("height")
    var height: Int? = null

)