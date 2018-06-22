package x.aichen.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import java.io.File
import java.lang.Double
import java.text.DecimalFormat
import java.text.NumberFormat


/**
 * Created by ac on 2017/4/6.
 */

object Util {


    /**
     * 格式化金额

     * @param s
     * *
     * @param len 保留几位小数
     * *
     * @return
     */
    fun formatMoney(s: String?, len: Int): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        var zero = StringBuffer()
        var formater: NumberFormat?
        val num = Double.parseDouble(s)
        formater = if (len == 0) {
//            formater = DecimalFormat("###,###")
            DecimalFormat("######")

        } else {
            val buff = StringBuffer()

            buff.append("######.")
            for (i in 0 until len) {
                buff.append("#")
                zero.append("0")
            }
            DecimalFormat(buff.toString())
        }
        var result = formater.format(num)
        if (result.indexOf(".") == -1)
            result = "" + result + "." + zero.toString()
        return result.toString()
    }

    fun getRealFilePath(context: Context, uri: Uri): String? {
        if (null == uri) return null
        val scheme = uri!!.scheme
        var data: String? = null
        if (scheme == null)
            data = uri!!.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri!!, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * 创建对应目录到android缓存目录下
     *
     * @return
     */
    fun CreateCacheFile(context: Context, childrenName: String): File? {
        var cache = context.applicationContext.externalCacheDir
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






}
