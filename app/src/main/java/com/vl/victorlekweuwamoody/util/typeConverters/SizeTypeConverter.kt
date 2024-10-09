package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Size
import com.google.gson.Gson

class SizeTypeConverter {

    @TypeConverter
    fun fromSize(size: Size) : String {
        val gson = Gson()
        val jsonString = gson.toJson(size)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toSize(sizeString: String) : Size {
        val gson = Gson()
        val sizeFromJson = gson.fromJson(sizeString, Size::class.java)
        println(sizeFromJson)
        return sizeFromJson
    }

}