package ren.imyan.image2latex.ui.main

import android.os.Bundle
import android.view.*
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