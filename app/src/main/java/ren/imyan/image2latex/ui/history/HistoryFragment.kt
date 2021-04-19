package ren.imyan.image2latex.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ren.imyan.fragment.BaseFragment
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

    val adapter by lazy {
        HistoryListAdapter()
    }

    override fun initView() {
        binding.showHistoryList.apply {
            layoutManager = GridLayoutManager(activity, 1)
            adapter = this@HistoryFragment.adapter
        }
    }

    override fun loadData() {
        viewModel.getHistoryData()

        viewModel.historyData.data.observe(this) {
            adapter.setNewData(it)
        }
    }
}