package code.keyur.mylistdemo.ui.slideshow

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import code.keyur.mylistdemo.BuildConfig
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import android.os.Bundle

import android.content.pm.PackageManager

import android.content.pm.ApplicationInfo
import java.lang.Exception


class SlideshowViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = SlideshowViewModel::class.java.simpleName
    val _sampleData = MutableLiveData<List<MySampleData>>()
    val _records = MutableLiveData<List<Record>>()
    val _context = application.baseContext
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

    fun fetchDataFromServer() {
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
                "https://api.data.gov.in/resource/de59e770-2333-4eaf-9088-a3643de040c8?api-key=${myApiKey}&format=json&offset=0&limit=40"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                run {
                    Log.d(TAG, "fetchDataFromServer: $response")
                    parseResponse(response)
                }

            }, { errResponse ->
                run {
                    Log.d(TAG, "fetchDataFromServer: Error: $errResponse")
                }
            })

            queue.add(jsonObjectRequest)
        }

    }

    private fun parseResponse(response: JSONObject?) {
        val resultObject = Gson().fromJson(response.toString(), MySampleData::class.java)
        Log.d(TAG, "parseResponse: $resultObject")
        if (resultObject != null) {
            if (resultObject.records != null) {
                _records.postValue(resultObject.records!!)
            }
        }
    }
}