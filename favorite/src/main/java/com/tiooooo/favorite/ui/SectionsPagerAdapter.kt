package com.tiooooo.favorite.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tiooooo.favorite.R
import com.tiooooo.favorite.ui.movie.MovieFavoriteFragment
import com.tiooooo.favorite.ui.tvseries.TvSeriesFavoriteFragment

class SectionsPagerAdapter(fa: FragmentActivity) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = TAB_TITLES.size

    fun getTitle(position: Int, context: Context): String =
        context.resources.getString(TAB_TITLES[position])


    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MovieFavoriteFragment()
        } else {
            TvSeriesFavoriteFragment()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_series)
    }
}