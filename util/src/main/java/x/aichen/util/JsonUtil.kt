package x.aichen.util

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.util.*

/**
 * String转JSON
 */
object JsonUtil {
    private var gson: Gson? = null

    private fun getGson(): Gson {
        if (null == gson)
            synchronized(JsonUtil::class.java) {
                if (null == gson)
                    gson = Gson()
            }
        return gson!!
    }

    /**
     * 从字符串转位对应的对象

     * @param str
     * *
     * @param beanObj
     * *
     * @return
     */
    fun <T> stringToObj(str: String, beanObj: Class<T>): T {
        var json: T? = null
        try {
            json = getGson().fromJson(str, beanObj)
        } catch (ex: Exception) {
            LogUtils.e(ex)
        }
        return json!!
    }


    /**

     * @param jsonString
     * *
     * @param cls
     * *
     * @param <T>
     * *
     * @return
    </T> */
    fun <T> objectToList(jsonString: String, cls: Class<T>): ArrayList<T> {
        val list = ArrayList<T>()
        try {
            val gson = Gson()
            val arry = JsonParser().parse(jsonString).asJsonArray
            for (jsonElement in arry) {
                list.add(gson.fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * 从对象转为String

     * @param object
     * *
     * @return
     */

    fun objToString(obj: Any): String {
        return getGson().toJson(obj)
    }

}
