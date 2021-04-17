package ren.imyan.image2latex.ui.about

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ren.imyan.image2latex.R

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 17:17
 * @website https://imyan.ren
 */
class AboutFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.about, rootKey)
        val test = Preference(context).apply {
            title = "test"
        }
        preferenceScreen.addPreference(test)
    }
}