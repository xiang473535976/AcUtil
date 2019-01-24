package x.aichen.extend

import android.app.Activity
import com.blankj.utilcode.util.ActivityUtils

/**
 * 艾晨------>2019/1/24
 * 全局扩展
 */


/**
 * 当前的act
 */

fun currentAct(): Activity {
    return ActivityUtils.getTopActivity()
}

/**
 * 长toast
 */
fun toastLong(msg: Any?) {
    msg?.let { android.widget.Toast.makeText(currentAct(), msg.toString(), android.widget.Toast.LENGTH_LONG).show() }
}

/**
 * toast
 */
fun toast(msg: Any?) {
    msg?.let { android.widget.Toast.makeText(currentAct(), msg.toString(), android.widget.Toast.LENGTH_SHORT).show() }
}
