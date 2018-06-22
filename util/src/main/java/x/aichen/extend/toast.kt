package x.aichen.extend

import android.content.Context
import com.blankj.utilcode.util.ToastUtils

fun Context.longToast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}

fun Context.toast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}
