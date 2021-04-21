package ren.imyan.image2latex.data.model.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 20:26
 * @website https://imyan.ren
 */
@JsonClass(generateAdapter = true)
data class MathpixRequestOption(
    @Json(name = "src")
    val imageBase64: String
)
