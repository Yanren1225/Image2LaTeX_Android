package ren.imyan.image2latex.core

import android.app.Application
import android.content.Context
import org.scilab.forge.jlatexmath.core.AjLatexMath

class App : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AjLatexMath.init(this)
    }
}