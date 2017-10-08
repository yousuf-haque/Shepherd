package com.yohaq.rxextensions

import io.reactivex.Observable

/**
 * Created by yousufhaque on 10/7/17.
 */
fun <T> T.just() = Observable.just(this)!!