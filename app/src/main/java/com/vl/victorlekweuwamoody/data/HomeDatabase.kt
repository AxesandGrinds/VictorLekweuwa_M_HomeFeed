package com.vl.victorlekweuwamoody.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vl.victorlekweuwamoody.api.models.HomeResponse
import com.vl.victorlekweuwamoody.util.typeConverters.AttributesTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.CardTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.CardsTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.DescriptionTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.FontTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.ImageTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.PageTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.SizeTypeConverter
import com.vl.victorlekweuwamoody.util.typeConverters.TitleTypeConverter

/*
* This room database contains the type converters for the complex model files.
* Without the type converters, it would not be possible to to convert the
* json server response into the model files.
* */
@Database(entities = [HomeResponse::class], version = 1)
@TypeConverters(
    AttributesTypeConverter::class,
    CardTypeConverter::class,
    CardsTypeConverter::class,
    DescriptionTypeConverter::class,
    FontTypeConverter::class,
    ImageTypeConverter::class,
    PageTypeConverter::class,
    SizeTypeConverter::class,
    TitleTypeConverter::class
)
abstract class HomeDatabase : RoomDatabase()  {

    abstract fun homeDao() : HomeDao

}