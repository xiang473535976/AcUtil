package x.aichen.extend

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import x.aichen.R

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



/**
 * 设置全屏
 */
fun Activity.setFullScreen() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}
/**
 * 加载进度条
 */
fun Context.showProgressDialog(): AlertDialog {
    val builder = AlertDialog.Builder(this, R.style.ProgreeDialog)
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null)
    builder.setView(view)
    val dialog = builder.create()
    dialog.setCanceledOnTouchOutside(false)
    return dialog
}


