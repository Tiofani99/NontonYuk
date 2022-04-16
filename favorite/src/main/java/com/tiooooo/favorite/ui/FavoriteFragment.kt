package com.tiooooo.favorite.ui

import com.google.android.material.tabs.TabLayoutMediator
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.favorite.R
import com.tiooooo.favorite.databinding.FragmentFavoriteBinding
import com.tiooooo.favorite.di.favoriteModule
import com.tiooooo.nontonyuk.ui.base.MainActivity
import org.koin.core.context.loadKoinModules

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, MainActivity>() {

    override fun getLayoutId(): Int = R.layout.fragment_favorite

    override fun initView() {
        super.initView()
        loadKoinModules(favoriteModule)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = sectionsPagerAdapter.getTitle(position, requireContext())
        }.attach()
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}