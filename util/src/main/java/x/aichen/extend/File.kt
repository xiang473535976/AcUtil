package x.aichen.extend

import android.content.Context
import android.os.Environment
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import java.io.File

/**
 * 创建对应目录到android缓存目录下
 *
 * @return
 */
fun Context.createCacheFile(childrenName: String): File? {
    var cache = applicationContext.externalCacheDir
    if (null == cache) {
        val sdCard = Environment.getExternalStorageDirectory().absolutePath
        val cacheString = "$sdCard/Android/data/${AppUtils.getAppPackageName()}/cache"
        LogUtils.e(cacheString)
        cache = File(cacheString)
        if (!cache.exists()) {
            cache.mkdir()
        }
    }
    if (cache.exists()) {
        val appBaseDir = File(cache, childrenName)
        if (!appBaseDir.exists()) {
            appBaseDir.mkdir()
        }
        return appBaseDir
    }
    return null
}
