package com.tiooooo.core.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<out VB : ViewBinding, out A : Activity> : Fragment() {
    val parentActivity: A by lazy { activity as A }
    private var _binding: VB? = null
    val binding get() = _binding!!

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open fun initView() {}
    protected open fun initListener() {}
    protected open fun setSubscribeToLiveData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )

        _binding = method.invoke(null, inflater, container, false) as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        setSubscribeToLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}