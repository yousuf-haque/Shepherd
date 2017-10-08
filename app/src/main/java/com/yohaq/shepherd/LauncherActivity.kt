package com.yohaq.shepherd

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.yohaq.simplescreen.SimpleController
import kotlinx.android.synthetic.main.activity_launcher.*

class LauncherActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)


        router = Conductor.attachRouter(this, container_chfl, savedInstanceState)

        if(!router.hasRootController()){
            router.pushController(RouterTransaction.with(SimpleController("1").also { it.onNext.subscribe { Log.d(TAG, "next clicked inside flow") } }))
        }
    }

    override fun onBackPressed() {
        if(!router.handleBack()) {
            super.onBackPressed()
        }
    }

    companion object {
        private val TAG = LauncherActivity::class.java.simpleName!!
    }
}
