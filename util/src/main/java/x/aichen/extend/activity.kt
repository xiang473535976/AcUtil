package x.aichen.extend

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.view.View
import com.blankj.utilcode.util.LogUtils

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
 *   https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 * 今日头条的屏幕适配方案
 * 如果设计图给的是dp  建议用这种    px的话 就用autolayout
 * designWidth 设计图宽度  下面假设设计图宽度是360dp，以宽维度来适配。
 */
private var sNonCompatDensity = 0f
private var sNonCompatScaleDensity = 0f
fun Activity.setCustomDensity(application: Application, designWidth: Int) {
    val appDisplayMetrics = application.resources.displayMetrics;
    if (sNonCompatDensity == 0f) {
        sNonCompatDensity = appDisplayMetrics.density;
        sNonCompatScaleDensity = appDisplayMetrics.scaledDensity;
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {

            }

            override fun onConfigurationChanged(newConfig: Configuration?) {
                sNonCompatScaleDensity = application.resources.displayMetrics.scaledDensity;
            }

        })

    }

//屏幕宽 的像素除以360 获得Density的值
//    val targetDensity = appDisplayMetrics.widthPixels / 360
    val targetDensity = appDisplayMetrics.widthPixels / designWidth
    val targetDensityDpi = targetDensity * 160
    val targetScaledDensity = targetDensity * (sNonCompatScaleDensity / sNonCompatDensity);
    appDisplayMetrics.density = targetDensity.toFloat()
    appDisplayMetrics.scaledDensity = targetScaledDensity
    appDisplayMetrics.densityDpi = targetDensityDpi

    val activityDisplayMetrics = application.resources.displayMetrics;
    activityDisplayMetrics.density = targetDensity.toFloat()
    activityDisplayMetrics.scaledDensity = targetScaledDensity
    activityDisplayMetrics.densityDpi = targetDensityDpi
}


