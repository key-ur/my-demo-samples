package code.keyur.mylistdemo.ui.slideshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "Field")
data class Field(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: String = "ppp-ppp",

    @ColumnInfo(name="name")
    @SerializedName("name")
    @Expose
    val name: String? = "",

    @ColumnInfo(name="type")
    @SerializedName("type")
    @Expose
    val type: String? = "",
){
    constructor():this("","","")
}