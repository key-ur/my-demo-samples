package code.keyur.mylistdemo.ui.slideshow

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Record(
    @SerializedName("cityname")
    @Expose
    val cityname: String? = null,

    @SerializedName("hospitalname")
    @Expose
    val hospitalname: String? = null,

    @SerializedName("hospitaladdress")
    @Expose
    val hospitaladdress: String? = null,

    var isLiked: Boolean = false,
)