package ren.imyan.image2latex.data.room.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ren.imyan.image2latex.data.model.Base64Image

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-19 21:05
 * @website https://imyan.ren
 */
class Base64ImageConverter {
    @TypeConverter
    fun storeBase64ImageFromString(base64Image: Base64Image): String = base64Image.base64

    @TypeConverter
    fun getBase64ImageFromString(value: String): Base64Image = Base64Image(value)
}