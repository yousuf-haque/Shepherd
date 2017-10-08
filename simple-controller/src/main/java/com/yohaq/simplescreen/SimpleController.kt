package com.yohaq.simplescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.jakewharton.rxbinding2.view.clicks
import com.yohaq.conductorextensions.postAttachEvents
import com.yohaq.conductorextensions.preDestroyEvents
import io.reactivex.Completable
import io.reactivex.rxkotlin.switchOnNext
import kotlinx.android.synthetic.main.controller_simple_screen.view.*

/**
 * Created by yousufhaque on 10/7/17.
 */ class SimpleController(bundle: Bundle): Controller(bundle) {
    constructor(content: String): this(Bundle().apply { putString(CONTENT, content) })

    private val content: String = bundle.getString(CONTENT)

    val onNext: Completable
    val onNavigateUp: Completable
    val onBackPressed: Completable

    init {
        val viewStream = postAttachEvents().map { it.view} .takeUntil(preDestroyEvents()).share()

        viewStream.subscribe { it.tv_content.text = content }
        onNext = viewStream.map { it.btn_next.clicks() }.switchOnNext().firstOrError().toCompletable()
        onNavigateUp = Completable.never()
        onBackPressed = Completable.never()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View = inflater.inflate(R.layout.controller_simple_screen, container, false)

    companion object {
        private val TAG = SimpleController::class.java.simpleName!!

        private const val CONTENT = "content"
    }
}
