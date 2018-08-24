package x.aichen.http.subsciber

import x.aichen.extend.toast
import x.aichen.http.ErrorTips
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import io.reactivex.observers.DisposableObserver
import x.aichen.extend.showProgressDialog


/**
 * Author：xjw         Mail：xiangjiwei@bmsoft.com.cn
 * CreateDate：2018/8/24      CreateVersion：1.0.0
 * CreateDesc：
 * UpdateDate：2018/8/24      CreateVersion：
 * UpdateDesc：
 */

abstract class BaseSubscriber<T>(showDialog: Boolean) : DisposableObserver<T>() {
    private val dialog by lazy { ActivityUtils.getTopActivity().showProgressDialog() }

    constructor() : this(false)

    init {
        if (showDialog) {
            dialog.show()
            //取消弹窗，就取消了网络请求
            dialog.setOnCancelListener { dispose() }
        }
    }

    override fun onComplete() {
        onRequestEnd()
    }

    override fun onError(t: Throwable) {
        onRequestEnd()
        var msg = ErrorTips.handleException(t).msg
        if (ObjectUtils.isNotEmpty(msg))
            ActivityUtils.getTopActivity().toast(ErrorTips.handleException(t).msg)
        LogUtils.e("异常\n$t")
    }

    override fun onNext(t: T) {
    }


    private fun onRequestEnd() {
        dialog.dismiss()
    }
}

