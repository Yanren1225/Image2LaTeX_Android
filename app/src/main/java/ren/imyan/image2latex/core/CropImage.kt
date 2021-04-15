package ren.imyan.image2latex.core

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import java.io.File

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-15 9:12
 * @website https://imyan.ren
 */
class CropImage : ActivityResultContract<CropImageResult, ActivityResult>() {
    var outUri: Uri? = null
    override fun createIntent(context: Context, input: CropImageResult): Intent {
        //把CropImageResult转换成裁剪图片的意图
        val intent = Intent("com.android.camera.action.CROP")
        val mimeType = context.contentResolver.getType(input.uri)
        val imageName = "${System.currentTimeMillis()}.${
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        }"
        outUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            Uri.fromFile(File(context.externalCacheDir!!.absolutePath, imageName))
        }
        context.grantUriPermission(
            context.packageName,
            outUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra("noFaceDetection", true) //去除默认的人脸识别，否则和剪裁匡重叠
        intent.setDataAndType(input.uri, mimeType)
        intent.putExtra("crop", "true") // crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("output", outUri)
        intent.putExtra("outputFormat", "JPEG") // 返回格式
        intent.putExtra("return-data", true)


        if (input.outputX != 0 && input.outputY != 0) {
            intent.putExtra("outputX", input.outputX)
            intent.putExtra("outputY", input.outputY)
        }
        if (input.aspectX != 0 && input.aspectY != 0) {
            if (input.aspectY == input.aspectX && Build.MANUFACTURER == "HUAWEI") {
                intent.putExtra("aspectX", 9999)
                intent.putExtra("aspectY", 9998)
            } else {
                intent.putExtra("aspectX", input.aspectX)
                intent.putExtra("aspectY", input.aspectY)
            }
        }

        return intent
    }

    //接收意图并处理数据
    override fun parseResult(resultCode: Int, intent: Intent?): ActivityResult {
        intent?.data = outUri
        return ActivityResult(resultCode, intent)
    }
}

/**
 * uri:需要裁剪的图片
 * aspect:长宽比例
 * output:图片输出长宽
 * uri 要裁剪的图片
 * aspect 剪裁比例
 * output 输入图片长宽
 */
class CropImageResult(
    val uri: Uri,
    val aspectX: Int = 0,
    val aspectY: Int = 0,
    @androidx.annotation.IntRange(from = 0, to = 1080)
    val outputX: Int = 0,
    @androidx.annotation.IntRange(from = 0, to = 1080)
    val outputY: Int = 0
)