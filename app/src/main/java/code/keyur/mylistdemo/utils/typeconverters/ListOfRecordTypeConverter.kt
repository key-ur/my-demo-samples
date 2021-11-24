package code.keyur.mylistdemo.utils.typeconverters

import androidx.room.TypeConverter
import code.keyur.mylistdemo.ui.slideshow.Record
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListOfRecordTypeConverter {
    private val type = object : TypeToken<List<Record>>() {}.type

    @TypeConverter
    @JvmStatic
    fun listOfRecordToString(listOfRecord: List<Record>): String {
        return Gson().toJson(listOfRecord, type)
    }

    @TypeConverter
    @JvmStatic
    fun stringToListOfRecord(string: String): List<Record> {
        return Gson().fromJson(string, type)
    }
}
