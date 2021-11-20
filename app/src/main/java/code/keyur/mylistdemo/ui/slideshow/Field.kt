package code.keyur.mylistdemo.ui.slideshow

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Field(
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,
)