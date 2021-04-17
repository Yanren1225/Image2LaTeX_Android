package ren.imyan.image2latex.util

import android.widget.Toast
import ren.imyan.base.ActivityCollector.currActivity

/**
 * @author EndureBlaze/炎忍 https://github.com.EndureBlaze
 * @data 2020-12-05 16:08
 * @website https://imyan.ren
 */

fun toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(currActivity, string, duration).show()
}

@JvmName("toast1")
fun String.toast(duration: Int = Toast.LENGTH_SHORT) {
    toast(this, duration)
}

fun Number.toast(duration: Int = Toast.LENGTH_SHORT) {
    toast(this.toString(), duration)
}