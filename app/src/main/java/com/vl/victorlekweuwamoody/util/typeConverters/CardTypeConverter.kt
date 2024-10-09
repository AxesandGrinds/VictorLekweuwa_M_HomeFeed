package com.vl.victorlekweuwamoody.util.typeConverters

import androidx.room.TypeConverter
import com.vl.victorlekweuwamoody.api.models.Card
import com.google.gson.Gson

class CardTypeConverter {

    @TypeConverter
    fun fromCard(card: Card) : String {
        val gson = Gson()
        val jsonString = gson.toJson(card)
        println(jsonString)
        return jsonString
    }

    @TypeConverter
    fun toCard(cardString: String) : Card {
        val gson = Gson()
        val cardFromJson = gson.fromJson(cardString, Card::class.java)
        println(cardFromJson)
        return cardFromJson
    }

}