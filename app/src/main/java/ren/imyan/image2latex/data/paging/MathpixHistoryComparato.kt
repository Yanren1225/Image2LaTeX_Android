package ren.imyan.image2latex.data.paging

import androidx.recyclerview.widget.DiffUtil
import ren.imyan.image2latex.data.model.MathpixHistory

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-21 21:41
 * @website https://imyan.ren
 */
object MathpixHistoryComparato : DiffUtil.ItemCallback<MathpixHistory>() {
    override fun areItemsTheSame(oldItem: MathpixHistory, newItem: MathpixHistory): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: MathpixHistory, newItem: MathpixHistory): Boolean {
        return oldItem == newItem
    }
}
