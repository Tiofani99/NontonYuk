package com.tiooooo.nontonyuk.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.navigation.Navigation
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentSplashBinding
import com.tiooooo.nontonyuk.ui.base.MainActivity
import com.tiooooo.nontonyuk.ui.base.SplashScreenActivity
import com.tiooooo.nontonyuk.ui.onboard.config.OnBoardConfig


class SplashFragment : BaseFragment<FragmentSplashBinding, SplashScreenActivity>() {
    override fun getLayoutId(): Int = R.layout.fragment_splash
    override fun initView() {
        super.initView()
        Handler(Looper.getMainLooper()).postDelayed({
            if (OnBoardConfig(requireContext()).isFirstTimeLaunch()) {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_splashScreenFragment_to_onBoardFragment)
                }
            } else {
                startActivity(Intent(view?.context, MainActivity::class.java))
                activity?.finish()
            }
        }, 3000)
    }
}