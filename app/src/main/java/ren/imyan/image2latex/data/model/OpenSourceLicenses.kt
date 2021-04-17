package ren.imyan.image2latex.data.model

import ren.imyan.image2latex.enum.License

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-16 16:48
 * @website https://imyan.ren
 */
data class OpenSourceLicenses(
    val name: String,
    val developer: String,
    val license: License,
    val link: String
)