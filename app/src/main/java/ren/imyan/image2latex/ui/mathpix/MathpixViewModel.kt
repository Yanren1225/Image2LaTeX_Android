package ren.imyan.image2latex.ui.mathpix

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ren.imyan.image2latex.core.ServiceCreator
import ren.imyan.image2latex.data.MutableConvertData
import ren.imyan.image2latex.data.model.ConvertData
import ren.imyan.image2latex.data.model.moshi.MathpixRequest
import ren.imyan.image2latex.data.model.moshi.MathpixResponse
import ren.imyan.image2latex.data.retrofit.MathpixService
import ren.imyan.image2latex.util.SP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.ByteArrayOutputStream

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:49
 * @website https://imyan.ren
 */
class MathpixViewModel : ViewModel() {

    private val appID by SP.string("app_id", "", "ren.imyan.image2latex_preferences")
    private val appKey by SP.string("app_key", "", "ren.imyan.image2latex_preferences")

    val bitmap = MutableLiveData<Bitmap>()

    val mathpixData: ConvertData<MathpixResponse>
        get() = _mathpixData.toImmutable()

    private val _mathpixData = MutableConvertData<MathpixResponse>()

    fun getMathpixData() {
        val mathpixService = ServiceCreator.create<MathpixService>()
        val base64 = bitmap.value?.let { bitmap2Base64(it) }!!

        val data1 = JSONObject().apply {
            put("src", "data:image/png;base64,$base64")
        }

        val requestBody = data1.toString().toRequestBody("application/json".toMediaTypeOrNull());

        mathpixService.getMathpixData(appID, appKey, requestBody)
            .enqueue(object : Callback<MathpixResponse> {
                override fun onResponse(
                    call: Call<MathpixResponse>,
                    response: Response<MathpixResponse>
                ) {
                    response.body()?.let {
                        if (it.error?.isEmpty() == true) {
                            _mathpixData.state.value = it.error
                        } else {
                            _mathpixData.data.value = it
                        }
                    }
                    response.errorBody()?.let {
                        val jsonObject = JSONObject(it.string())
                        _mathpixData.state.value = jsonObject.getString("error")
                    }
                }

                override fun onFailure(call: Call<MathpixResponse>, t: Throwable) {
                    _mathpixData.state.value = t.message
                }
            })
    }

    private fun bitmap2Base64(bitmap: Bitmap): String {
        return ByteArrayOutputStream().run {
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, this)
            val bytes: ByteArray = this.toByteArray()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        }
    }
}