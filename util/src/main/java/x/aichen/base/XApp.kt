package x.aichen.base

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * author 艾晨
 * Created at 2017/3/31 12:44
 * Update at 2017/3/31 12:44
 * Update people:
 * Version:1.0
 * 说明：  基类APP
 */

abstract class XApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this) //utilcode 初始化
        AnasyMemory()
    }


    private fun AnasyMemory() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

}
