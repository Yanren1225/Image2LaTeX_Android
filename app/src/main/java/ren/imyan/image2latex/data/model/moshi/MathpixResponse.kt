package ren.imyan.image2latex.data.model.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 18:21
 * @website https://imyan.ren
 */
@JsonClass(generateAdapter = true)
data class MathpixResponse(
    @Json(name = "request_id")
    val requestID: String?,

    @Json(name = "text")
    val text: String?,

    @Json(name = "latex_styled")
    val latexStyled: String?,

    @Json(name = "confidence")
    val confidence: Float?,

    @Json(name = "confidence_rate")
    val confidenceRate: Float?,

    @Json(name = "data")
    val data: ResponseData?,

    @Json(name = "html")
    val html: String?,

    @Json(name = "is_printed")
    val isPrinted: Boolean?,

    @Json(name = "is_handwritten")
    val isHandWritten: Boolean?,

    @Json(name = "auto_rotate_confidence")
    val autoRotateConfidence: Float?,

    @Json(name = "auto_rotate_degrees")
    val autoRotateDegrees: Float?,

    @Json(name = "error")
    val error: String?,

    @Json(name = "error_info")
    val errorInfo: ErrorInfo?
) {
    @JsonClass(generateAdapter = true)
    data class ResponseData(
        @Json(name = "type")
        val type: String,

        @Json(name = "value")
        val value: String,
    )

    @JsonClass(generateAdapter = true)
    data class ErrorInfo(
        @Json(name = "id")
        val id: String,

        @Json(name = "message")
        val message: String,

        @Json(name = "detail")
        val detail: String?
    )
}

