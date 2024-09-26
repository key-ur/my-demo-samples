package code.keyur.mylistdemo.utils.typeconverters

import androidx.room.TypeConverter
import code.keyur.mylistdemo.ui.slideshow.Field
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListOfFieldTypeConverter {
    private val type = object : TypeToken<List<Field>>() {}.type

    @TypeConverter
    @JvmStatic
    fun listOfFieldToString(listOfField: List<Field>): String {
        return Gson().toJson(listOfField, type)
    }

    @TypeConverter
    @JvmStatic
    fun stringToListOfField(string: String): List<Field> {
        return Gson().fromJson(string, type)
    }
}
