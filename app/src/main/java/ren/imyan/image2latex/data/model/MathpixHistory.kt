package ren.imyan.image2latex.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ren.imyan.image2latex.data.room.converter.Base64ImageConverter

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-18 22:20
 * @website https://imyan.ren
 */
@Entity(tableName = "history")
data class MathpixHistory(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "image_filename") val imageFilename: String,
)