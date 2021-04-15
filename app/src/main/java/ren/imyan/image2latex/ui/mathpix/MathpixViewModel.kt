package ren.imyan.image2latex.ui.mathpix

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:49
 * @website https://imyan.ren
 */
class MathpixViewModel : ViewModel() {
    val bitmap = MutableLiveData<Bitmap>()
}