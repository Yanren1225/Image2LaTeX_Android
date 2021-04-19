package ren.imyan.image2latex.ui.history

import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ren.imyan.base.ActivityCollector.currActivity
import ren.imyan.image2latex.R
import ren.imyan.image2latex.data.model.MathpixHistory
import ren.imyan.image2latex.databinding.ItemHistoryBinding
import ren.imyan.image2latex.util.loadImageFromFile
import ren.imyan.recyclerviewadapter.BaseRecyclerViewAdapter

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-19 21:15
 * @website https://imyan.ren
 */
class HistoryListAdapter(data: List<MathpixHistory> = listOf()) :
    BaseRecyclerViewAdapter<MathpixHistory, ItemHistoryBinding>(
        data.toMutableList(),
        R.layout.item_history
    ) {

    override fun bindItem(itemBinding: ItemHistoryBinding, itemData: MathpixHistory) {
        val fileName = itemData.imageFilename
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = currActivity.loadImageFromFile(fileName)
            Glide.with(currActivity).load(bitmap).into(itemBinding.showImage)
        }

    }
}