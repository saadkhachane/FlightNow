package com.xardev.flightnow.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.trampoline
import io.reactivex.rxjava3.schedulers.TestScheduler

class TrampolineSchedulers : BaseSchedulers {
    override fun io(): Scheduler {
        return trampoline()
    }

    override fun computation(): Scheduler {
        return trampoline()
    }

    override fun ui(): Scheduler {
        return trampoline()
    }
}