package code.keyur.mylistdemo.utils


/**
 *  + What happens in this class?
=>


 */
import com.google.gson.Gson

interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)
}

inline fun <reified T : JSONConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)