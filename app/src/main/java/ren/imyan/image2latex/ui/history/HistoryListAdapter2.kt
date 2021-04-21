package ren.imyan.image2latex.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ren.imyan.base.ActivityCollector
import ren.imyan.base.ActivityCollector.currActivity
import ren.imyan.image2latex.data.model.MathpixHistory
import ren.imyan.image2latex.databinding.ItemHistoryBinding
import ren.imyan.image2latex.util.loadImageFromFile

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-21 21:30
 * @website https://imyan.ren
 */
class HistoryListAdapter2(diffCallback: DiffUtil.ItemCallback<MathpixHistory>) :
    PagingDataAdapter<MathpixHistory, HistoryListAdapter2.HistoryViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MathpixHistory) {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = currActivity.loadImageFromFile(item.imageFilename)
                Glide.with(currActivity).load(bitmap).into(binding.showImage)
            }
        }
    }
}