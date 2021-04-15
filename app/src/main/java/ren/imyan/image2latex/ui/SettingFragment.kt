package ren.imyan.image2latex.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ren.imyan.image2latex.R


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-15 9:49
 * @website https://imyan.ren
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
    }

}