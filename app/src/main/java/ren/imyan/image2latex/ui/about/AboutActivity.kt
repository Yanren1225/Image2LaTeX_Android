package ren.imyan.image2latex.ui.about

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ren.imyan.base.BaseUIActivity
import ren.imyan.base.startActivity
import ren.imyan.image2latex.R
import ren.imyan.image2latex.databinding.ActivityAboutBinding
import ren.imyan.image2latex.util.AppInfoUtil

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 16:59
 * @website https://imyan.ren
 */
class AboutActivity : BaseUIActivity<ActivityAboutBinding, AboutViewModel>() {

    companion object {
        fun action(context: Context) {
            startActivity<AboutActivity>(context)
        }
    }

    override fun initViewModel(): AboutViewModel =
        ViewModelProvider(this)[AboutViewModel::class.java]

    override fun initBinding(): ActivityAboutBinding = ActivityAboutBinding.inflate(layoutInflater)

    override fun initToolbar(): Pair<Toolbar, *> =
        Pair(binding.layoutToolbar.toolbar, R.string.about)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setHomeButtonEnabled(true);
            setDisplayHomeAsUpEnabled(true);
        }
        supportFragmentManager
            .beginTransaction()
            .replace(binding.aboutFragment.id, AboutFragment())
            .commit()
        binding.setVersion("${AppInfoUtil.versionName} <${AppInfoUtil.versionCode}>")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}