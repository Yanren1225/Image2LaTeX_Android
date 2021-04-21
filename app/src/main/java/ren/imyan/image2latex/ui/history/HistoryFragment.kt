package ren.imyan.image2latex.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ethanhua.skeleton.Skeleton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ren.imyan.fragment.BaseFragment
import ren.imyan.image2latex.R
import ren.imyan.image2latex.data.paging.MathpixHistoryComparato
import ren.imyan.image2latex.databinding.FragmentHistoryBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-19 20:26
 * @website https://imyan.ren
 */
class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryViewModel>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

    override fun initViewModel(): HistoryViewModel =
        ViewModelProvider(this)[HistoryViewModel::class.java]

    private val adapter by lazy {
        HistoryListAdapter2(MathpixHistoryComparato)
    }

    private val skeletonScreen by lazy {
        Skeleton.bind(binding.showHistoryList)
            .adapter(adapter)
            .load(R.layout.item_history)
            .build()
    }

    override fun initView() {
        binding.showHistoryList.apply {
            layoutManager = GridLayoutManager(activity, 1)
            adapter = this@HistoryFragment.adapter
        }
//        skeletonScreen.show()
    }

    override fun loadData() {

        lifecycleScope.launch {
            viewModel.historyData.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}