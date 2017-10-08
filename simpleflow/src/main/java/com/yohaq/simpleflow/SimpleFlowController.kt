package com.yohaq.simpleflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.yohaq.conductorextensions.ControllerLifecycleEvent
import com.yohaq.conductorextensions.lifecycleStream
import com.yohaq.rxextensions.just
import com.yohaq.simplescreen.SimpleController
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.ofType
import kotlinx.android.synthetic.main.controller_simple_flow.view.*

/**
 * Created by yousufhaque on 10/7/17.
 */
class SimpleFlowController(bundle: Bundle) : Controller(bundle) {
    constructor(steps: Array<String>) : this(Bundle().apply { putStringArray(STEPS, steps) })

    private val steps: Array<String> = bundle.getStringArray(STEPS)

    val onFlowComplete: Completable

    val onUpPressed: Completable = Completable.never()

    val onBackPressed: Completable = Completable.never()

    init {

        val controllers = steps.map { SimpleController(it) }
        val routerTransaction = controllers.map { RouterTransaction.with(it).just().takeUntil(it.onNext.toSingle { Unit }.toObservable()) }
        val transactionStream = Observable.concat(routerTransaction)
        onFlowComplete = Completable.concat(controllers.map { it.onNext })
        val routerStream: Observable<Router> = lifecycleStream()
                .ofType<ControllerLifecycleEvent.PostCreateView>()
                .map { it.view.container }
                .map { getChildRouter(it) }

        Observable.combineLatest(
                routerStream,
                transactionStream,
                BiFunction { router: Router, transaction: RouterTransaction ->
            { router.pushController(transaction) }
        })



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View = inflater.inflate(R.layout.controller_simple_flow, container, false)


    companion object {
        private val TAG = SimpleFlowController::class.java.simpleName!!
        private const val STEPS = "steps"
    }
}