package code.keyur.mylistdemo.ui.slideshow

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import code.keyur.mylistdemo.utils.JSONConvertable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "Record", primaryKeys = ["cityname", "hospitalname", "hospitaladdress"])
data class Record(

    @ColumnInfo(name = "cityname")
    @NonNull
    @SerializedName("cityname")
    @Expose
    val cityname: String = "",

    @ColumnInfo(name = "hospitalname")
    @NonNull
    @SerializedName("hospitalname")
    @Expose
    val hospitalname: String = "",

    @ColumnInfo(name = "hospitaladdress")
    @NonNull
    @SerializedName("hospitaladdress")
    @Expose
    val hospitaladdress: String = "",

    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false,
):JSONConvertable {
    constructor() : this( "", "", "", false)
}