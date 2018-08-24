package x.aichen.extend

import android.support.v4.app.Fragment
import android.view.View


/**
 * author 艾晨
 * Created at 2018/7/6 17:09
 * Update at 2018/7/6 17:09
 * Update people:
 * Version:1.0
 * 说明：
*/


/**
 * 关闭当前activity
 */
fun Fragment.BackClick(view: View) {
    view.setOnClickListener {
        activity?.finish()
    }
}

