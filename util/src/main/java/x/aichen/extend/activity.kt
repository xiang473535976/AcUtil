package x.aichen.extend

import android.app.Activity
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
fun Activity.BackClick(view: View) {
    view.setOnClickListener {
        finish()
    }
}

fun <V : View> Activity.fd(_id: Int): V {
    return findViewById<V>(_id) //as V
}

