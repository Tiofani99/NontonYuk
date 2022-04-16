package com.tiooooo.nontonyuk.ui.onboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentOnBoardBinding
import com.tiooooo.nontonyuk.ui.base.MainActivity
import com.tiooooo.nontonyuk.ui.base.SplashScreenActivity
import com.tiooooo.nontonyuk.ui.onboard.config.OnBoardConfig
import com.tiooooo.nontonyuk.ui.onboard.config.SlideAdapter


class OnBoardFragment : BaseFragment<FragmentOnBoardBinding, SplashScreenActivity>() {

    override fun getLayoutId(): Int  = R.layout.activity_splash_screen
    private lateinit var slideAdapter: SlideAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private val sliderChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            when (position) {
                0 -> {
                    binding.btnNext.showView()
                    binding.btnBack.hideView()
                }
                else -> {
                    binding.btnNext.showView()
                    binding.btnBack.showView()
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSet()
        interactions(view)
    }

    override fun initView() {
        super.initView()
        layouts = arrayOf(
            R.layout.onboarding_slide1,
            R.layout.onboarding_slide2,
            R.layout.onboarding_slide3
        )

        slideAdapter = SlideAdapter(requireContext(), layouts)
    }

    private fun dataSet() {
        addBottomDots(0)
        binding.slider.apply {
            adapter = slideAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun interactions(view: View) {
        binding.btnBack.setOnClickListener {
            val current = getCurrentScreen(-1)
            if (current < layouts.size) binding.slider.currentItem = current
            else navigateToLogin(view)
        }
        binding.btnSkip.setOnClickListener { navigateToLogin(view) }
        binding.btnNext.setOnClickListener {
            val current = getCurrentScreen(+1)
            if (current < layouts.size) binding.slider.currentItem = current
            else navigateToLogin(view)
        }
    }

    private fun navigateToLogin(view: View) {
        OnBoardConfig(requireContext()).setFirstTimeLaunch(false)
        startActivity(Intent(view.context, MainActivity::class.java))
        activity?.finish()
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        binding.dotsLayout.removeAllViews()
        for (i in dots!!.indices) {
            dots!![i] = TextView(requireContext())
            dots!![i]?.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(getColor(requireContext(), R.color.colorGrey))
            binding.dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(getColor(requireContext(), R.color.primary))
        }
    }

    private fun getCurrentScreen(i: Int): Int = binding.slider.currentItem.plus(i)

}