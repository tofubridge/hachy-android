package ms.imagine.foodiemate.Presenter


import ms.imagine.foodiemate.api.AzureClient
import ms.imagine.foodiemate.api.AzurePrediction
import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.utils.Eulog
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class AzurePresenter(val callBacks: AzureCallBacks) : retrofit2.Callback<AzurePrediction> {
    private val azureClient: AzureClient

    init {
        val builder = Retrofit.Builder()
                .baseUrl(AzureClient.baseURL)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        azureClient = retrofit.create(AzureClient::class.java)
    }

    fun postImageFromUrl(remote_url: String) {
        azureClient.postImageFromUrl(buildUrl(remote_url)).enqueue(this)
    }

    private fun buildUrl(remote_url: String): HashMap<String, String>{
        val map = HashMap<String, String>()
        map.put("url", remote_url)
        return map
    }

    override fun onFailure(call: retrofit2.Call<AzurePrediction>?, t: Throwable?) {
        println("error")
        println (t.toString())
        println (call)
        callBacks.onAzureFailure("Cannot Call")
    }

    override fun onResponse(call: retrofit2.Call<AzurePrediction>?, response: retrofit2.Response<AzurePrediction>?) {
        val azurePrediction: AzurePrediction? = response?.body()
        println("PDC" + response.toString() + azurePrediction)
        if (azurePrediction == null || azurePrediction.predictions == null){
            callBacks.onAzureFailure(response.toString())
        } else {

            val predictions = azurePrediction.predictions!!
            callBacks.onAzureSuccess(predictions)
        }
    }
}