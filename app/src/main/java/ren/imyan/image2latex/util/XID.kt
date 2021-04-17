package ren.imyan.image2latex.util

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import ren.imyan.base.ActivityCollector
import ren.imyan.base.ActivityCollector.currActivity
import ren.imyan.image2latex.R

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-17 21:01
 * @website https://imyan.ren
 */
fun getLocalString(@StringRes id: Int): String =
    currActivity.resources.getString(id)


@SuppressLint("SupportAnnotationUsage")
@StringRes
fun Int.string(): String = currActivity.resources.getString(this)
