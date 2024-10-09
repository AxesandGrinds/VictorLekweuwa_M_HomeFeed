package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Attributes
import com.google.gson.Gson

class AttributesTypeConverter {

    @TypeConverter
    fun fromAttributes(attributes: Attributes) : String {
        val gson = Gson()
        val jsonString = gson.toJson(attributes)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toAttributes(attributesString: String) : Attributes {
        val gson = Gson()
        val attributesFromJson = gson.fromJson(attributesString, Attributes::class.java)
        println(attributesFromJson)
        return attributesFromJson
    }

}