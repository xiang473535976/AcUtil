package aichen.green.ww.http

import x.aichen.http.bean.Dto
import x.aichen.http.subsciber.BaseSubscriber


/**
 * Created by 艾晨 on 2017/1/3.
 */

abstract class ABaseSubscriber<T : Dto<*>>(showDialog: Boolean) : BaseSubscriber<T>(showDialog) {
    constructor() : this(false)

    abstract fun onSuccess(t: T)
    override fun onNext(t: T) {
        super.onNext(t)
        if (t.code == 200)
            onSuccess(t)
    }
}

