package ms.imagine.foodiemate.Presenter


import android.util.Log
import ms.imagine.foodiemate.activity.BaseActivity
import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.data.EggStagePossibility
import ms.imagine.foodiemate.utils.Eulog
import okhttp3.*
import java.io.IOException

class AzurePresenter(val callBacks: AzureCallBacks) {

    companion object {
        const val AZURE_VIEWREC_URL = "https://southcentralus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/6ed4e03b-5c8d-4cc1-9cc0-ef527e4f625f/url?iterationId=31f38b48-43e6-43ff-a3c3-4638be6d0f52"
        const val TEST_IMG_URL = "https://www.ifauna.cz/images/nforum-foto/prew/201204/4f998f80e6b2b.jpg"
        const val AZURE_VIEWREC_FILE = "" // not used yet
        fun body (st: String): String {
            return "{\"Url\": \"" + st +  " \"}"
        }
    }

    fun dispatch(link: String) {

        var client = OkHttpClient();
        var mediaType= MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, body(link));
        var request = Request.Builder()
                .url(AZURE_VIEWREC_URL)
                .post(body)
                .addHeader("prediction-key", "4e26554b300f47478e5c880d2a6492d7")
                .addHeader("content-type", "application/json")
                .build();
        Log.d("EUGWARN", "Async request")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = callBacks.onAzureFailure()
            override fun onResponse(call: Call, response: Response){
                val str = response.body()?.string()
                if (str != null && response.code() == 200  ) {
                    var q = EggStagePossibility.create(str)
                    callBacks.onAzureSuccess(q)
                } else {
                    callBacks.onAzureFailure()
                }
                println(response.body()?.string())
            }
        })


    }





}