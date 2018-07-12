package aichen.green.ww.http

import x.aichen.extend.toast
import x.aichen.http.ErrorTips
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import x.aichen.extend.showProgressDialog
import x.aichen.http.bean.Dto


/**
 * Created by 艾晨 on 2017/1/3.
 */

abstract class BaseSubscriber<T : Dto<*>>(showDialog: Boolean) : Observer<T> {
    private val dialog by lazy { ActivityUtils.getTopActivity().showProgressDialog()  }
    private var disposable: Disposable? = null

    constructor() : this(false)

    init {
        if (showDialog) {
            dialog.show()
            //取消弹窗，就取消了网络请求
            dialog.setOnCancelListener {
                if (!disposable!!.isDisposed)
                    disposable!!.dispose()
            }
        }
        onStart()
    }

    override fun onComplete() {
        onRequestEnd()
    }

    override fun onError(t: Throwable) {
        onRequestEnd()
        var msg = ErrorTips.handleException(t)?.msg
        if (ObjectUtils.isNotEmpty(msg))
            ActivityUtils.getTopActivity().toast(ErrorTips.handleException(t).msg)
        LogUtils.e("异常\n" + t)
    }

    override fun onNext(t: T) {
        if (null != t)
            if (t.code == 200) {
                onSuccess(t)
            } else {
                ActivityUtils.getTopActivity().toast(t.msg)
            }
        else
            ActivityUtils.getTopActivity(). toast("网络异常")
    }

    override fun onSubscribe(d: Disposable) {
        this.disposable = d
    }

    abstract fun onSuccess(t: T)

    /**
     * 开始执行
     */
    fun onStart() {

    }

    private fun onRequestEnd() {
        dialog.dismiss()
    }
}

