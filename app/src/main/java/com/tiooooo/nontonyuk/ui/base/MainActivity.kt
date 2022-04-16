package com.tiooooo.nontonyuk.ui.base

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ActivityMainBinding
import com.tiooooo.nontonyuk.service.MovieReminder
import com.tiooooo.nontonyuk.ui.dashboard.movie.MovieFragment
import com.tiooooo.nontonyuk.ui.dashboard.tvseries.TvSeriesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        navController = findNavController(R.id.content_main)
        setupActionBarWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom, menu)
        binding.bottomBar.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    //jika tidak menggunakan nav component
    private fun setUpNavigationBottom() {
        val movieFragment = MovieFragment()
        val tvSeriesFragment = TvSeriesFragment()
        val favoriteFragment = instantiateFragment("com.tiooooo.favorite.ui.FavoriteFragment")
        val mFragmentManager: FragmentManager = supportFragmentManager
        var active: Fragment = movieFragment

        mFragmentManager.beginTransaction().add(R.id.content_main, movieFragment)
            .commit()
        mFragmentManager.beginTransaction().add(R.id.content_main, tvSeriesFragment)
            .hide(tvSeriesFragment).commit()
        favoriteFragment?.let {
            mFragmentManager.beginTransaction().add(R.id.content_main, it).hide(favoriteFragment)
                .commit()
        }

        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    mFragmentManager.beginTransaction().hide(active).show(movieFragment).commit()
                    active = movieFragment
                }

                1 -> {
                    mFragmentManager.beginTransaction().hide(active).show(tvSeriesFragment).commit()
                    active = tvSeriesFragment
                }

                2 -> {
                    mFragmentManager.beginTransaction().hide(active).show(favoriteFragment!!)
                        .commit()
                    active = favoriteFragment
                }
            }
        }
    }

    private fun instantiateFragment(className: String): Fragment? {
        return try {
            Class.forName(className).newInstance() as Fragment
        } catch (e: Exception) {
            null
        }
    }
}