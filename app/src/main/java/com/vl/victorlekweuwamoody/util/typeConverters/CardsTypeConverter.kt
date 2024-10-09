package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Cards
import com.google.gson.Gson

class CardsTypeConverter {

    @TypeConverter
    fun fromCards(cards: Cards) : String {
        val gson = Gson()
        val jsonString = gson.toJson(cards)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toCards(cardsString: String) : Cards {
        val gson = Gson()
        val cardsFromJson = gson.fromJson(cardsString, Cards::class.java)
        println(cardsFromJson)
        return cardsFromJson
    }

}