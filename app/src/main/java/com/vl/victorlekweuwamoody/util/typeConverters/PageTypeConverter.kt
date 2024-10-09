package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Page
import com.google.gson.Gson

class PageTypeConverter {

    @TypeConverter
    fun fromPage(page: Page) : String {
        val gson = Gson()
        val jsonString = gson.toJson(page)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toPage(pageString: String) : Page {
        val gson = Gson()
        val pageFromJson = gson.fromJson(pageString, Page::class.java)
        println(pageFromJson)
        return pageFromJson
    }

}