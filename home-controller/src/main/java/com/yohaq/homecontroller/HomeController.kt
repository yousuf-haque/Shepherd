package com.yohaq.homecontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller

/**
 * Created by yousufhaque on 10/7/17.
 */
class HomeController: Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_home, container, false)
}