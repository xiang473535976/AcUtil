package x.aichen.extend

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.LogUtils
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
 *   https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 * 今日头条的屏幕适配方案
 * 如果设计图给的是dp  建议用这种    px的话 就用autolayout
 * designWidth 设计图宽度  下面假设设计图宽度是360dp，以宽维度来适配。
 */
private var sNonCompatDensity = 0f
private var sNonCompatScaleDensity = 0f
fun Activity.setCustomDensity(designWidth: Int) {
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
//    val activityDisplayMetrics = application.resources.displayMetrics;   8.0上的  修改这个    不生效  需要修改act的 才行
    val activityDisplayMetrics = resources.displayMetrics
    activityDisplayMetrics.density = targetDensity.toFloat()
    activityDisplayMetrics.scaledDensity = targetScaledDensity
    activityDisplayMetrics.densityDpi = targetDensityDpi
}

/**
 * 屏幕截图
 */
fun Activity.screenShot(activity: Activity, isDeleteStatusBar: Boolean = true): Bitmap {
    val decorView = activity.window.decorView
    decorView.isDrawingCacheEnabled = true
    decorView.buildDrawingCache()
    val bmp = decorView.drawingCache
    val dm = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(dm)
    var ret: Bitmap? = null
    if (isDeleteStatusBar) {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight)
    } else {
        ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
    }
    decorView.destroyDrawingCache()
    return ret!!
}

/**
 * 是否竖屏
 */
fun Activity.isPortrait(): Boolean {
    return resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT
}

/**
 * 是否横屏
 */
fun Activity.isLandscape(): Boolean {
    return resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE
}


/**
 * 设置竖屏
 */
fun Activity.setPortrait() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

/**
 * 设置横屏
 */
fun Activity.setLandscape() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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


