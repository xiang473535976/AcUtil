package x.aichen.extend

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.ToastUtils

/**
 * longToast
 */
fun Context.longToast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}

fun Fragment.longToast(msg: Any?) {
    context?.longToast(msg)
}

fun Activity.longToast(msg: Any?) {
    longToast(msg)
}

fun Context.toast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}

fun Fragment.toast(msg: Any?) {
    context?.toast(msg)
}

fun Activity.toast(msg: Any?) {
    toast(msg)
}
