package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Image (

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("size")
    var size: Size? = Size()

)