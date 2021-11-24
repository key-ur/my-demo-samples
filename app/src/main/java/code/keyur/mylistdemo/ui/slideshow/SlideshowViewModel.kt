package code.keyur.mylistdemo.ui.slideshow

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

import android.content.pm.PackageManager

import android.content.pm.ApplicationInfo
import code.keyur.mylistdemo.room.MyRoomDatabase
import java.lang.Exception


class SlideshowViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = SlideshowViewModel::class.java.simpleName
    val _sampleData = MutableLiveData<List<MySampleData>>()
    val _records = MutableLiveData<List<Record>>()
    val _context = application.baseContext
    private val myRoomDatabase = MyRoomDatabase.getMyDatabase(_context)
    private var offset: Int = 0
    private var limit: Int = 30
    val miscResults = MutableLiveData<Int>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun loadDummyData() {
        val tempList = mutableListOf<MySampleData>()
//        for (i in 0..30) {
//            tempList.add(MySampleData(i, "Title-$i", "Description: ${i}"))
//        }
        _sampleData.postValue(tempList)
    }

    private fun fetchDataFromServer() {
        var myApiKey: String = ""
        try {
            val ai: ApplicationInfo = _context.getPackageManager().getApplicationInfo(
                _context.getPackageName(),
                PackageManager.GET_META_DATA
            )
            val bundle = ai.metaData
            myApiKey = bundle.getString("mygovtdata.apikey").toString()
        } catch (e: Exception) {
            Log.e(
                TAG,
                "Dear developer. Don't forget to configure <meta-data android:name=\"my_test_metagadata\" android:value=\"testValue\"/> in your AndroidManifest.xml file."
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val queue = Volley.newRequestQueue(_context)

            val url =
                "https://api.data.gov.in/resource/de59e770-2333-4eaf-9088-a3643de040c8?api-key=${myApiKey}&format=json&offset=$offset&limit=$limit"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                run {
                    miscResults.postValue(200)
                    Log.d(TAG, "fetchDataFromServer: $response")
                    parseResponse(response)
                }

            }, { errResponse ->
                run {
//                    miscResults.postValue(errResponse.networkResponse.statusCode)
                    Log.d(TAG, "fetchDataFromServer: Error: $errResponse")
                }
            })

            queue.add(jsonObjectRequest)
        }

    }

    private fun parseResponse(response: JSONObject?) {
        val resultObject = Gson().fromJson(response.toString(), MySampleData::class.java)
        Log.d(TAG, "parseResponse: $resultObject")
        viewModelScope.launch(Dispatchers.IO) {
            if (resultObject != null) {
                val tempResult =
                    myRoomDatabase.mySampleDataDao().insertOneOrMoreMySampleData(resultObject)
                Log.d(TAG, "parseResponse: insert oper.:$tempResult")

                if (tempResult.isNotEmpty()) {
                    val totalRecords = resultObject.total
//                    if (totalRecords > resultObject.records!!.size) {
//                        Log.d(TAG, "parseResponse: fetching rest of the records..")
//                        offset = resultObject.records!!.size
//                        limit = totalRecords - resultObject.records!!.size
//                        Log.d(TAG, "parseResponse: offset: $offset -- limit: $limit")
//                        fetchDataFromServer()
//                    } else {
//                        Log.d(TAG, "parseResponse: not fetching records!")
//                    }
                }
                if (resultObject.records != null) {
                    _records.postValue(resultObject.records!!)
                    val resultOpInsertRecords = myRoomDatabase.recordDao()
                        .insertRecords(*resultObject.records!!.map { it }.toTypedArray())
                    Log.d(TAG, "parseResponse: insert records oper. result: $resultOpInsertRecords")
                }
            }
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = myRoomDatabase.recordDao().getAllRecords()
            if (tempList.size > 0) {

                _records.postValue(tempList)
            } else {
                fetchDataFromServer()
            }
        }

    }

    fun updateRecord(record: Record) {
        Log.d(TAG, "updateRecord: 1..")
        viewModelScope.launch(Dispatchers.IO) {
            val tempRecList = mutableListOf<Record>()
            tempRecList.add(record)
            val updateRecOp = myRoomDatabase.recordDao().updateRecord(record)
            Log.d(TAG, "updateRecord: $updateRecOp")
        }
    }

    fun fetchMoreData(currentRecordCount: Int) {
        offset = currentRecordCount
        fetchDataFromServer()
    }
}