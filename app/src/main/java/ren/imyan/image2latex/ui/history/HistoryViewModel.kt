package ren.imyan.image2latex.ui.history

import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ren.imyan.image2latex.core.App
import ren.imyan.image2latex.core.AppDatabase
import ren.imyan.image2latex.data.MutableConvertData
import ren.imyan.image2latex.data.model.ConvertData
import ren.imyan.image2latex.data.model.MathpixHistory
import ren.imyan.image2latex.data.model.moshi.MathpixResponse

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-19 20:28
 * @website https://imyan.ren
 */
class HistoryViewModel : ViewModel() {
    private val db by lazy {
        Room.databaseBuilder(
            App.appContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    val historyData: ConvertData<List<MathpixHistory>>
        get() = _historyData.toImmutable()

    private val _historyData = MutableConvertData<List<MathpixHistory>>()

    fun getHistoryData() {
        CoroutineScope(Dispatchers.Main).launch {
            val allData = getAllHistoryData()
            _historyData.data.value = allData
        }
    }

    private suspend fun getAllHistoryData() = withContext(Dispatchers.IO) {
        db.historyDao().getAll()
    }
}