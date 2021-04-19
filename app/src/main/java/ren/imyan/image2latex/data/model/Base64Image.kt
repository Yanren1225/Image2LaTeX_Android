package ren.imyan.image2latex.data.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-18 23:02
 * @website https://imyan.ren
 */
data class Base64Image(val base64: String) {
    val bitmap: Bitmap
        get() {
            val bytes: ByteArray = Base64.decode(base64, Base64.NO_WRAP)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

    override fun toString(): String = base64
}
