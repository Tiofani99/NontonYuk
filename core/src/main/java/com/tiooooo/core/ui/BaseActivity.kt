package com.tiooooo.core.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open fun initView() {}
    protected open fun initListener() {}
    protected open fun setSubscribeToLiveData() {}

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        performViewBinding()
        requestedOrientation.apply {
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        initView()
        initListener()
        setSubscribeToLiveData()
    }

    private fun performViewBinding() {
        _binding = ViewBinding { layoutInflater.inflate(getLayoutId(), null) } as VB
        setContentView(binding.root)
    }


}