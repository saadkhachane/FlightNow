package com.xardev.flightnow.utils

import io.reactivex.rxjava3.core.Scheduler

interface BaseSchedulers {

    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler

}