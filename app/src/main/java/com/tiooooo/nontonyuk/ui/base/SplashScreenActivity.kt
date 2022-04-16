package com.tiooooo.nontonyuk.ui.base

import android.annotation.SuppressLint
import com.tiooooo.core.ui.BaseActivity
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_splash_screen
}