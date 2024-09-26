package code.keyur.mylistdemo.ui.slideshow

import androidx.room.*
import code.keyur.mylistdemo.utils.typeconverters.ListOfFieldTypeConverter
import code.keyur.mylistdemo.utils.typeconverters.ListOfRecordTypeConverter
import code.keyur.mylistdemo.utils.typeconverters.ListOfStringTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MySampleData")
data class MySampleData(
    @PrimaryKey(autoGenerate = true)
    val index:Int,

    @SerializedName("index_name")
    @Expose
    var indexName: String = "xx-xxxxkx-xxx",

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = "",

    @ColumnInfo(name = "desc")
    @SerializedName("desc")
    @Expose
    var desc: String? = "",

    @ColumnInfo(name = "created")
    @SerializedName("created")
    @Expose
    var created: Long = 0L,

    @ColumnInfo(name = "updated")
    @SerializedName("updated")
    @Expose
    var updated: Long = 0L,

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    @Expose
    var createdDate: String? = "",

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updatedDate: String? = "",

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    var active: String? = "",

    @ColumnInfo(name = "visualizable")
    @SerializedName("visualizable")
    @Expose
    var visualizable: String? = "",

    @ColumnInfo(name = "catalog_uuid")
    @SerializedName("catalog_uuid")
    @Expose
    var catalogUuid: String? = "",

    @ColumnInfo(name = "source")
    @SerializedName("source")
    @Expose
    var source: String? = "",

    @ColumnInfo(name = "org_type")
    @SerializedName("org_type")
    @Expose
    var orgType: String? = "",

    @TypeConverters(ListOfStringTypeConverter::class)
    @SerializedName("org")
    @Expose
    var org: List<String>? = emptyList(),

    @TypeConverters(ListOfStringTypeConverter::class)
    @SerializedName("sector")
    @Expose
    var sector: List<String>? = emptyList(),

//    @Embedded
    @TypeConverters(ListOfFieldTypeConverter::class)
    @SerializedName("field")
    @Expose
    var field: List<Field>? = emptyList(),

    @ColumnInfo(name = "external_ws_url")
    @SerializedName("external_ws_url")
    @Expose
    var externalWsUrl: String? = "",

    @ColumnInfo(name = "external_ws")
    @SerializedName("external_ws")
    @Expose
    var externalWs: String? = "",

    @ColumnInfo(name = "message")
    @SerializedName("message")
    @Expose
    var message: String? = "",

    @ColumnInfo(name = "version")
    @SerializedName("version")
    @Expose
    var version: String? = "",

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    var status: String? = "",

    @ColumnInfo(name = "total")
    @SerializedName("total")
    @Expose
    var total: Int = 0,

    @ColumnInfo(name = "count")
    @SerializedName("count")
    @Expose
    var count: Int = 0,

    @ColumnInfo(name = "limit")
    @SerializedName("limit")
    @Expose
    var limit: String? = "",

    @ColumnInfo(name = "offset")
    @SerializedName("offset")
    @Expose
    var offset: String? = "",

//    @Embedded
    @TypeConverters(ListOfRecordTypeConverter::class)
    @ColumnInfo(name = "records")
    @SerializedName("records")
    @Expose
    var records: List<Record>? = emptyList(),
) {
    constructor() : this(0,
        "",
        "",
        "",
        0L,
        0L,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        emptyList(),
        emptyList(),
        emptyList(),
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        emptyList()
    )
}