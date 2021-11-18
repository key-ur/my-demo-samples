package code.keyur.mylistdemo.ui.slideshow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MySampleData (
    @SerializedName("index_name")
    @Expose
    var indexName: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("desc")
    @Expose
    var desc: String? = null,

    @SerializedName("created")
    @Expose
    var created:Long= 0L,

    @SerializedName("updated")
    @Expose
    var updated:Long = 0L,

    @SerializedName("created_date")
    @Expose
    var createdDate: String? = null,

    @SerializedName("updated_date")
    @Expose
    var updatedDate: String? = null,

    @SerializedName("active")
    @Expose
    var active: String? = null,

    @SerializedName("visualizable")
    @Expose
    var visualizable: String? = null,

    @SerializedName("catalog_uuid")
    @Expose
    var catalogUuid: String? = null,

    @SerializedName("source")
    @Expose
    var source: String? = null,

    @SerializedName("org_type")
    @Expose
    var orgType: String? = null,

    @SerializedName("org")
    @Expose
    var org: List<String>? = null,

    @SerializedName("sector")
    @Expose
    var sector: List<String>? = null,

    @SerializedName("field")
    @Expose
    var field: List<Field>? = null,

    @SerializedName("external_ws_url")
    @Expose
    var externalWsUrl: String? = null,

    @SerializedName("external_ws")
    @Expose
    var externalWs: String? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("version")
    @Expose
    var version: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("total")
    @Expose
    var total:Int = 0,

    @SerializedName("count")
    @Expose
    var count:Int = 0,

    @SerializedName("limit")
    @Expose
    var limit: String? = null,

    @SerializedName("offset")
    @Expose
    var offset: String? = null,

    @SerializedName("records")
    @Expose
    var records: List<Record>? = null,
)