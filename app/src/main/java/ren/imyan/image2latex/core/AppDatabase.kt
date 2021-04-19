package ren.imyan.image2latex.core

import androidx.room.Database
import androidx.room.RoomDatabase
import ren.imyan.image2latex.data.model.MathpixHistory
import ren.imyan.image2latex.data.room.dao.HistoryDao

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-18 23:16
 * @website https://imyan.ren
 */
@Database(entities = [MathpixHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
