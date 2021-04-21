package ren.imyan.image2latex.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi


/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-20 11:13
 * @website https://imyan.ren
 */
inline fun <reified E> Any.toJson(): String {
    val moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<E> =
        moshi.adapter(E::class.java)
    return jsonAdapter.toJson(this as E)
}
