package ren.imyan.image2latex.data.retrofit

import okhttp3.RequestBody
import ren.imyan.image2latex.data.model.moshi.MathpixResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 17:56
 * @website https://imyan.ren
 */
interface MathpixService {
    @POST("v3/text")
    fun getMathpixData(
        @Header("app_id") appID: String,
        @Header("app_key") appKey: String,
        @Body data: RequestBody
    ): Call<MathpixResponse>
}