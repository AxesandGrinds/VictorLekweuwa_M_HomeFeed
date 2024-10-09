package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Font
import com.google.gson.Gson

class FontTypeConverter {

    @TypeConverter
    fun fromFont(font: Font) : String {
        val gson = Gson()
        val jsonString = gson.toJson(font)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toFont(fontString: String) : Font {
        val gson = Gson()
        val fontFromJson = gson.fromJson(fontString, Font::class.java)
        println(fontFromJson)
        return fontFromJson
    }

}