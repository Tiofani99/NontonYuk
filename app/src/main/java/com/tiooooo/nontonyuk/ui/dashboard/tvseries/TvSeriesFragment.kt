package com.tiooooo.nontonyuk.ui.dashboard.tvseries

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.DataState
import com.tiooooo.core.utils.Resource
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentTvSeriesBinding
import com.tiooooo.nontonyuk.ui.base.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class TvSeriesFragment : BaseFragment<FragmentTvSeriesBinding, MainActivity>() {

    override fun getLayoutId(): Int = R.layout.fragment_tv_series
    private val viewModel: TvSeriesViewModel by viewModel()

    override fun setSubscribeToLiveData() {
        super.setSubscribeToLiveData()
        viewModel.fetchTvSeries().observe(viewLifecycleOwner) { tvSeries ->
            if (tvSeries != null) {
                when (tvSeries) {
                    is Resource.Loading -> setDataState(DataState.LOADING)
                    is Resource.Success -> {
                        setDataState(DataState.SUCCESS)
                        setupAdapter(tvSeries.data!!)
                    }
                    is Resource.Error -> setDataState(DataState.ERROR, tvSeries.message)
                }
            }
        }
    }

    private fun setDataState(state: DataState, message: String? = null) {
        binding.apply {
            when (state) {
                DataState.LOADING -> {
                    rvTvSeries.hideView()
                    layoutLoading.root.showView()
                    layoutError.root.hideView()
                }
                DataState.SUCCESS -> {
                    layoutLoading.root.hideView()
                    layoutError.root.hideView()
                    rvTvSeries.showView()
                }
                DataState.ERROR -> {
                    rvTvSeries.hideView()
                    layoutLoading.root.hideView()
                    layoutError.apply {
                        root.showView()
                        tvInfo.text = message
                        btnTryAgain.setOnClickListener {
                            root.hideView()
                            layoutLoading.root.showView()
                            setSubscribeToLiveData()
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter(data: List<TvSeries>?) {
        val tvSeriesAdapter = TvSeriesAdapter(data!!)
        binding.rvTvSeries.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = tvSeriesAdapter
        }
    }

    override fun onDestroyView() {
        binding.rvTvSeries.adapter = null
        super.onDestroyView()
    }

}