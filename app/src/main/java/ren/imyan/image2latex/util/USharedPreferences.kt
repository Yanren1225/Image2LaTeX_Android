package ren.imyan.image2latex.util

import android.content.Context
import android.content.SharedPreferences
import ren.imyan.image2latex.core.App
import kotlin.reflect.KProperty

/**
 * @author EndureBlaze/炎忍 https://github.com.EndureBlaze
 * @data 2021-02-16 12:25
 * @website https://imyan.ren
 */
class SP<T>(private val key: String, private val defValue: Any, private val fileName: String) {

    companion object {
        fun string(key: String, defValue: String, fileName: String = "share_data"): SP<String> =
            SP(key, defValue, fileName)

        fun int(key: String, defValue: Int, fileName: String = "share_data"): SP<Int> =
            SP(key, defValue, fileName)

        fun long(key: String, defValue: Long, fileName: String = "share_data"): SP<Long> =
            SP(key, defValue, fileName)

        fun float(key: String, defValue: Float, fileName: String = "share_data"): SP<Float> =
            SP(key, defValue, fileName)

        fun boolean(key: String, defValue: Boolean, fileName: String = "share_data"): SP<Boolean> =
            SP(key, defValue, fileName)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        App.appContext.getSharedPreferences(
            fileName,
            Context.MODE_PRIVATE
        )
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        when (defValue) {
            is String -> sharedPreferences.getString(key, defValue)!!
            is Int -> sharedPreferences.getInt(key, defValue)
            is Long -> sharedPreferences.getLong(key, defValue)
            is Float -> sharedPreferences.getFloat(key, defValue)
            is Boolean -> sharedPreferences.getBoolean(key, defValue)
            else -> sharedPreferences.getString(key, defValue.toString())!!
        } as T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        sharedPreferences.edit().apply {
            when (value) {
                is String -> putString(key, value)!!
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> putString(key, value.toString())!!
            }
        }.apply()
}