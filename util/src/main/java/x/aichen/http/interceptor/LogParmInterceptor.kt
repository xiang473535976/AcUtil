package x.aichen.http.interceptor

import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 默认的Post参数打印
 * 如果要查看添加的公共参数  ===>ParmInterceptor
 *   需要在添加完公共参数之后 再添加这个     这样参数才打印完整
 */
class LogParmInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val originalRequest = chain!!.request()
        Log.e("xiang", "╔═══════════════════════════════════════════════════════════════════════════════════════════════════")
        Log.e("xiang", "║   " + originalRequest.url().toString())
        when (originalRequest.method()) {"POST" -> {
            if (originalRequest.body() is FormBody) {
                val body = originalRequest.body() as FormBody?
                for (i in 0 until body!!.size()) {
                    Log.e("参数", " ║    " + body.encodedName(i) + "    ====   " + body.encodedValue(i))
                }
            }
        }
            "GET" -> {

            }
        }
        Log.e("xiang", "╚═══════════════════════════════════════════════════════════════════════════════════════════════════")
        return chain?.proceed(originalRequest.newBuilder().build())!!
    }
}