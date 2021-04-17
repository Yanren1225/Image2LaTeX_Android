package ren.imyan.image2latex.ui.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ren.imyan.image2latex.R
import ren.imyan.image2latex.ui.about.AboutActivity


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-15 9:49
 * @website https://imyan.ren
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        when (preference?.key) {
            "about" -> context?.let { AboutActivity.action(it) }
        }

        return true
    }
}