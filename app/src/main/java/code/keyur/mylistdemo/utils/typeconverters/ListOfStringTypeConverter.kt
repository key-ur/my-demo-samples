package code.keyur.mylistdemo.utils.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ListOfStringTypeConverter {
    private val type: Type = object: TypeToken<List<String>>(){}.type

    @TypeConverter
    @JvmStatic
    fun objectToString(list:List<String>):String{
          return  Gson().toJson(list, type)
    }


    @TypeConverter
    @JvmStatic
    fun stringToObject(string: String):List<String>{
        return Gson().fromJson(string, type)
    }

}