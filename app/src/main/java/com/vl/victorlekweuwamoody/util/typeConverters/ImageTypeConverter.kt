package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Image
import com.google.gson.Gson

class ImageTypeConverter {

    @TypeConverter
    fun fromImage(image: Image) : String {
        val gson = Gson()
        val jsonString = gson.toJson(image)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toImage(imageString: String) : Image {
        val gson = Gson()
        val imageFromJson = gson.fromJson(imageString, Image::class.java)
        println(imageFromJson)
        return imageFromJson
    }

}