package ren.imyan.image2latex.ui.mathpix

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ren.imyan.image2latex.core.App
import ren.imyan.image2latex.core.AppDatabase
import ren.imyan.image2latex.core.ServiceCreator
import ren.imyan.image2latex.data.MutableConvertData
import ren.imyan.image2latex.data.model.ConvertData
import ren.imyan.image2latex.data.model.MathpixHistory
import ren.imyan.image2latex.data.model.moshi.MathpixRequestOption
import ren.imyan.image2latex.data.model.moshi.MathpixResponse
import ren.imyan.image2latex.data.retrofit.MathpixService
import ren.imyan.image2latex.util.SP
import ren.imyan.image2latex.util.base64
import ren.imyan.image2latex.util.saveImageToFile
import ren.imyan.image2latex.util.toJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:49
 * @website https://imyan.ren
 */
class MathpixViewModel(application: Application) : AndroidViewModel(application) {

    private val db by lazy {
        Room.databaseBuilder(
            App.appContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }


    private val appID by SP.string("app_id", "", "ren.imyan.image2latex_preferences")
    private val appKey by SP.string("app_key", "", "ren.imyan.image2latex_preferences")

    val bitmap = MutableLiveData<Bitmap>()

    val mathpixData: ConvertData<MathpixResponse>
        get() = _mathpixData.toImmutable()

    private val _mathpixData = MutableConvertData<MathpixResponse>()

    fun getMathpixData() = CoroutineScope(Dispatchers.IO).launch {

        saveHistory()

        val mathpixService = ServiceCreator.create<MathpixService>()
        val base64 = bitmap.value?.base64.toString()

        val option = JSONObject(MathpixRequestOption("data:image/png;base64,$base64").toJson<MathpixRequestOption>())

        val requestBody = option.toString().toRequestBody("application/json".toMediaTypeOrNull());

        mathpixService.getMathpixData(appID, appKey, requestBody)
            .enqueue(object : Callback<MathpixResponse> {
                override fun onResponse(
                    call: Call<MathpixResponse>,
                    response: Response<MathpixResponse>
                ) {
                    response.body()?.let {
                        if (it.error?.isNotEmpty() == true) {
                            _mathpixData.state.value = it.error.toString()
                        } else {
                            _mathpixData.data.value = it
                        }
                    }
                    response.errorBody()?.let {
                        kotlin.runCatching {
                            val jsonObject = JSONObject(it.string())
                            _mathpixData.state.value = jsonObject.getString("error")
                        }
                    }
                }

                override fun onFailure(call: Call<MathpixResponse>, t: Throwable) {
                    _mathpixData.state.value = t.message
                }
            })
    }

    private suspend fun saveHistory() = withContext(Dispatchers.IO) {
        bitmap.value?.let {
            val fileName = getApplication<App>().saveImageToFile(it).toString()
            db.historyDao().insertAll(MathpixHistory(imageFilename = fileName))
        }
    }
}