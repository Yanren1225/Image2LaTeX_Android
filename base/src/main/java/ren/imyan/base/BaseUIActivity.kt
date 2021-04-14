package ren.imyan.base

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import ren.imyan.base.util.QMUIStatusBarHelper


abstract class BaseUIActivity<viewBinding : ViewBinding, viewModel : ViewModel> : BaseActivity() {

    private var _binding: viewBinding? = null
    private var _viewModel: viewModel? = null
    private var isSetView = true
    private var isSetToolbar = true

    val binding get() = _binding!!
    val viewModel get() = _viewModel!!

    fun notSetView() {
        isSetView = false
    }

    fun notSetToolbar() {
        isSetToolbar = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getThemeManager().setAppTheme()
        _binding = initBinding()
        _viewModel = initViewModel()
        if (isSetView) {
            setContentView(binding.root)
        }
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window?.decorView?.let {
//                it.systemUiVisibility = (it.systemUiVisibility
//                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                window?.decorView?.post {
//                    if (window.decorView.rootWindowInsets?.systemWindowInsetBottom ?: 0 < Resources.getSystem().displayMetrics.density * 40) {
//                        window.navigationBarColor = Color.TRANSPARENT
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                            window.navigationBarDividerColor = Color.TRANSPARENT
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            window.isNavigationBarContrastEnforced = false
//                            window.isStatusBarContrastEnforced = false
//                        }
//                    }
//                }
//            }
//        }
    }

    abstract fun initViewModel(): viewModel

    abstract fun initBinding(): viewBinding

//    abstract fun initToolbar(): Pair<Toolbar, *>

    fun setToolBarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId);
    }

    fun setToolBarTitle(title: CharSequence) {
        supportActionBar?.title = title;
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun attachBaseContext(newBase: Context?) {
//        //如果不使用工具类也可以在这里处理好 Locale 传入
//        val context = newBase?.let {
//            wrap(newBase, LanguageUtil.getLocale(newBase))
//        }
//        super.attachBaseContext(context)
//    }
}