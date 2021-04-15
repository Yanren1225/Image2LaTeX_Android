package ren.imyan.image2latex.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ren.imyan.image2latex.ui.SettingFragment
import ren.imyan.image2latex.ui.mathpix.MathpixFragment

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 18:55
 * @website https://imyan.ren
 */
class MainViewModel : ViewModel() {

    val fragmentList: List<Fragment>
        get() = _fragmentList

    private val _fragmentList: MutableList<Fragment> by lazy {
        ArrayList()
    }

    init {
        _fragmentList.addAll(loadFragments())
    }

    private fun loadFragments(): ArrayList<Fragment> =
        arrayListOf(
            MathpixFragment(),
            SettingFragment(),
            SettingFragment()
        )
}