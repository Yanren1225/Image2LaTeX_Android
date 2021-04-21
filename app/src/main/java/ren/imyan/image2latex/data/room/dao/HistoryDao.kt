package ren.imyan.image2latex.data.room.dao

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import ren.imyan.image2latex.data.model.MathpixHistory

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-18 23:15
 * @website https://imyan.ren
 */
@Dao
interface HistoryDao {
    @Transaction
    @Query("SELECT * FROM history ORDER by uid desc")
    fun getAll(): PagingSource<Int, MathpixHistory>

    @Insert
    fun insertAll(vararg mathpixHistorys: MathpixHistory)

    @Delete
    fun delete(mathpixHistory: MathpixHistory)
}