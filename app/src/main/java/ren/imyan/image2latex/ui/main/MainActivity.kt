package ren.imyan.image2latex.ui.main

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ren.imyan.base.BaseUIActivity
import ren.imyan.image2latex.R
import ren.imyan.image2latex.databinding.ActivityMainBinding

class MainActivity : BaseUIActivity<ActivityMainBinding, MainViewModel>() {


    override fun initViewModel(): MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


    override fun initToolbar(): Pair<Toolbar, *> = Pair(
        binding.layoutToolbar.toolbar,
        R.string.app_name
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewPager()
        initBottomBar()

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = getThemeColorFromId(R.attr.colorOnPrimary)
            navigationBarColor = getThemeColorFromId(R.attr.colorSurface)

            if (delegate.localNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS,
                        APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }

    private fun initViewPager() {
        binding.viewpager.apply {
            adapter = ViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                viewModel.fragmentList
            )
            isUserInputEnabled = false
            offscreenPageLimit = 3
        }
    }

    private fun initBottomBar() {
        binding.navigation.setOnNavigationItemSelectedListener {
            binding.viewpager.currentItem = when (it.itemId) {
                R.id.bottom_nav_mathpix -> 0
                R.id.bottom_nav_history -> 1
                R.id.bottom_nav_setting -> 2
                else -> 0
            }

            return@setOnNavigationItemSelectedListener true
        }
    }
}