package x.aichen.extend

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.ToastUtils

/**
 * longToast
 */
fun Context.LongToast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}

fun Fragment.longToast(msg: Any?) {
    context?.LongToast(msg)
}

fun Activity.longToast(msg: Any?) {
    LongToast(msg)
}

fun Context.Toast(msg: Any?) {
    msg?.let { ToastUtils.showLong(msg.toString()) }
}

fun Fragment.toast(msg: Any?) {
    context?.Toast(msg)
}

fun Activity.toast(msg: Any?) {
    this.Toast(msg)
}
