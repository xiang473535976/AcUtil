package x.aichen.extend

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * 避免重复点击
 */
//不带过滤的普通点击
//view.click {
//    L.e("aaron click test")
//}
//带默认600毫秒过滤的点击事件（方法1）
//view.clickWithTrigger {
//    L.e("aaron clickWithTrigger test")
//}
//带默认600毫秒过滤的点击事件（方法2）
//view.withTrigger().click {
//    L.e("aaron click test")
//}
//带700毫秒过滤的点击事件（方法1）
//bind.clickWithTrigger(700) {
//    L.e("aaron clickWithTrigger test")
//}
//带700毫秒过滤的点击事件（方法2）
//view.withTrigger(700).click {
//    L.e("aaron click test")
//}
/***
 * 设置延迟时间的View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @return T
 */
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/***
 * 点击事件的View扩展   中间点击  间隔600
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickDelay300(block: (T) -> Unit) = clickWithTrigger(300, block)

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {

    if (clickEnable()) {
        block(it as T)
    }
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}


private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

/**
 * SmartRefreshLayout结束下拉刷新  结束上拉刷新
 */
fun SmartRefreshLayout.endSmRefresh() {
    finishLoadMore()
    finishRefresh()
}

/**
 * 设置颜色直接使用colors.xml中定义的颜色即可
 */
fun TextView.setColor(@ColorRes resId: Int) {
    this.setTextColor(ContextCompat.getColor(context, resId))
}

fun TextView.setDrawableLeft(@DrawableRes resId: Int) {
    var drawable = ContextCompat.getDrawable(context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setDrawableRight(@DrawableRes resId: Int) {
    var drawable = ContextCompat.getDrawable(context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setDrawableTop(@DrawableRes resId: Int) {
    var drawable = ContextCompat.getDrawable(context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}

fun TextView.setDrawableBottom(@DrawableRes resId: Int) {
    var drawable = ContextCompat.getDrawable(context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}
