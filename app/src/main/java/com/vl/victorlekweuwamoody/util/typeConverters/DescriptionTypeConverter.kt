package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Description
import com.google.gson.Gson

class DescriptionTypeConverter {

    @TypeConverter
    fun fromDescription(description: Description) : String {
        val gson = Gson()
        val jsonString = gson.toJson(description)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toDescription(descriptionString: String) : Description {
        val gson = Gson()
        val descriptionFromJson = gson.fromJson(descriptionString, Description::class.java)
        println(descriptionFromJson)
        return descriptionFromJson
    }

}