package x.aichen.extend

import android.support.v4.app.Fragment
import android.view.View

/**
 * Contract->Model->Presenter->Activity->Module->Component
 * author 艾晨
 * Created at 2017/6/9 16:19
 * Update at 2017/6/9 16:19
 * Update people:
 * Version:1.0
 * 说明：Kotlin的扩展功能
 */

/**
 * 关闭当前activity
 */
fun Fragment.BackClick(view: View) {
    view.setOnClickListener {
        activity?.finish()
    }
}


fun Fragment.longToast(msg: Any) {
    context?.longToast(msg)
}

fun Fragment.toast(msg: Any) {
    context?.toast(msg)
}
