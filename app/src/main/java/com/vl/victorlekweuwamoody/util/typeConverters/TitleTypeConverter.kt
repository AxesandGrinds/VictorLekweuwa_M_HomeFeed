package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Title
import com.google.gson.Gson

class TitleTypeConverter {

    @TypeConverter
    fun fromTitle(title: Title) : String {
        val gson = Gson()
        val jsonString = gson.toJson(title)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toTitle(titleString: String) : Title {
        val gson = Gson()
        val titleFromJson = gson.fromJson(titleString, Title::class.java)
        println(titleFromJson)
        return titleFromJson
    }

}