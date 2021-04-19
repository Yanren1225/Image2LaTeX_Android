package ren.imyan.image2latex.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import ren.imyan.image2latex.data.model.Base64Image
import java.io.ByteArrayOutputStream


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-18 22:57
 * @website https://imyan.ren
 */
val Bitmap.base64: Base64Image
    get() = ByteArrayOutputStream().run {
        compress(Bitmap.CompressFormat.PNG, 40, this)
        val bytes: ByteArray = this.toByteArray()
        Base64Image(Base64.encodeToString(bytes, Base64.NO_WRAP))
    }
