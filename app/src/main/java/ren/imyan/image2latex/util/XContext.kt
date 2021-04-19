package ren.imyan.image2latex.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-19 21:52
 * @website https://imyan.ren
 */
/**
 * 保存图片到私有 file 目录
 * @param bitmap 需要保存的 Bitmap
 * @param name （可选）文件名，默认为当前时间戳
 */
suspend fun Context.saveImageToFile(
    bitmap: Bitmap,
    name: String = System.currentTimeMillis().toString() + ".jpg"
) = withContext(Dispatchers.IO) {
    runCatching {
        openFileOutput(name, Context.MODE_PRIVATE).use {
            val output = ByteArrayOutputStream().apply {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            }
            it.write(output.toByteArray())
        }
        return@withContext name
    }
}

suspend fun Context.loadImageFromFile(name: String) = withContext(Dispatchers.IO) {
    kotlin.runCatching {
        openFileInput(name).readBytes().apply {
            return@withContext BitmapFactory.decodeByteArray(this, 0, this.size)
        }
    }
}