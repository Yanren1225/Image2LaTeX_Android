package ren.imyan.image2latex.ui.setting

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ren.imyan.image2latex.R
import ren.imyan.image2latex.ui.about.AboutActivity
import ren.imyan.image2latex.util.string


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-15 9:49
 * @website https://imyan.ren
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        val idAndKeySummaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                "${R.string.no_set.string()} ${preference.title}"
            } else {
                text
            }
        }

        findPreference<EditTextPreference>("app_id")?.apply {
            summaryProvider = idAndKeySummaryProvider
        }

        findPreference<EditTextPreference>("app_key")?.apply {
            summaryProvider = idAndKeySummaryProvider
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        when (preference?.key) {
            "about" -> context?.let { AboutActivity.action(it) }
        }

        return true
    }
}