package com.vl.victorlekweuwamoody.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Attributes (

    @SerializedName("text_color")
    var textColor: String? = null,

    @SerializedName("font")
    var font: Font?   = Font()

)