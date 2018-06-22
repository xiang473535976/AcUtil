/**
 * 2014-3-3 下午3:55:51 Created By niexiaoqiang
 */

package x.aichen.util

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

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
    fun <T> StringToObj(str: String, beanObj: Class<T>): T {
        var json: T? = null
        try {
            json = getGson().fromJson(str, beanObj)
        } catch (ex: Exception) {
            LogUtils.e(ex)
        }
        return json!!
    }


    /**
     * 从字符串转位对应的对象

     * @param <T>
     * *
     * @param str
     * *
     * @param beanObj
     * *
     * @return
    </T> */
    fun <T> StringToList(str: String, beanObj: Class<T>): List<T> {
        val type = object : TypeToken<List<T>>() {

        }.type
        val fromJson2 = gson!!.fromJson<Any>(str, type)
        val list = fromJson2 as List<T>
        return list
    }

    /**
     * 第一个报错的时候  用第二个   优先用第二个吧

     * @param jsonString
     * *
     * @param cls
     * *
     * @param <T>
     * *
     * @return
    </T> */
    fun <T> Object2List(jsonString: String, cls: Class<T>): ArrayList<T> {
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

    fun ObjToString(obj: Any): String {
        return getGson().toJson(obj)
    }

}
