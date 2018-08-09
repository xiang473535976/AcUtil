package x.aichen.http.interceptor

import okhttp3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*


/**
 * parms    添加参数的map集合
 * 动态添加公共参数
 * 支持方法
 * GET
 * POST
 */
class ParmInterceptor(private val parms: Map<String, Any>?) : Interceptor {
    val UTF8 = Charset.forName("UTF-8")
    private var mHttpUrl: HttpUrl? = null
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (parms != null && parms.isNotEmpty()) {
            if (request.method() == "GET") {
                mHttpUrl = request.url()
                request = addGetParamsSign(request)
            } else if (request.method() == "POST") {
                mHttpUrl = HttpUrl.parse(parseUrl(request.url().toString()))
                request = addPostParamsSign(request)
            }
        }
        return chain.proceed(request)
    }

    /**
     * 为get请求 添加签名和公共动态参数
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private fun addGetParamsSign(request: Request): Request {
        var request = request
        var httpUrl = request.url()
        val newBuilder = httpUrl.newBuilder()

        //获取原有的参数
        val nameSet = httpUrl.queryParameterNames()
        val nameList = arrayListOf<String>()
        nameList.addAll(nameSet)
        val oldParams = TreeMap<String, Any>()
        for (i in 0 until nameList.size) {
            val value = if (httpUrl.queryParameterValues(nameList.get(i)) != null && httpUrl.queryParameterValues(nameList.get(i)).size > 0) httpUrl.queryParameterValues(nameList.get(i)).get(0) else ""
            oldParams.put(nameList.get(i), value)
        }
        val nameKeys = Arrays.asList(nameList).toString()
        //拼装新的参数
        val newParams = updateDynamicParams(oldParams)
//        Utils.checkNotNull(newParams, "newParams==null")
        for (entry in newParams.entries) {
            val urlValue = URLEncoder.encode(entry.value.toString(), UTF8.name())
            //原来的URl: https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
            if (!nameKeys.contains(entry.key)) {//避免重复添加
                newBuilder.addQueryParameter(entry.key, urlValue)
            }
        }

        httpUrl = newBuilder.build()
        request = request.newBuilder().url(httpUrl).build()
        return request
    }

    /**
     * 为post请求 添加签名和公共动态参数
     *
     * @param request
     * @return
     */
    private fun addPostParamsSign(request: Request): Request {
        var newrequest = request
        when (request.body()) {
            is FormBody -> {
                val bodyBuilder = FormBody.Builder()
                var formBody: FormBody = request.body() as FormBody

                //原有的参数
                val oldParams = TreeMap<String, Any>()
                for (i in 0 until formBody.size()) {
                    oldParams.put(formBody.encodedName(i), formBody.encodedValue(i))
                }

                //拼装新的参数
                val newParams = updateDynamicParams(oldParams)
//            Utils.checkNotNull(newParams, "newParams == null")
                for (entry in newParams.entries) {
                    val value = URLDecoder.decode(entry.value.toString(), UTF8.name())
                    bodyBuilder.addEncoded(entry.key, value)
                }
                formBody = bodyBuilder.build()
                newrequest = request.newBuilder().post(formBody).build()
            }
            is MultipartBody -> {
                var multipartBody: MultipartBody = request.body() as MultipartBody
                val bodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
                val oldParts = multipartBody.parts()

                //拼装新的参数
                val newParts = arrayListOf<MultipartBody.Part>()
                newParts.addAll(oldParts)
                val oldParams = TreeMap<String, Any>()
                val newParams = updateDynamicParams(oldParams)
                for (paramEntry in newParams.entries) {
                    val part = MultipartBody.Part.createFormData(paramEntry.key, paramEntry.value.toString())
                    newParts.add(part)
                }
                for (part in newParts) {
                    bodyBuilder.addPart(part)
                }
                multipartBody = bodyBuilder.build()
                newrequest = request.newBuilder().post(multipartBody).build()
            }
            is RequestBody -> {
                val params = updateDynamicParams(TreeMap())
                val url = createUrlFromParams(mHttpUrl!!.url().toString(), params)
                newrequest = request.newBuilder().url(url).build()
            }
        }
        return newrequest
    }

    /**
     * 更新请求的动态参数
     *
     * @param dynamicMap
     * @return 返回新的参数集合
     */
    private fun updateDynamicParams(dynamicMap: TreeMap<String, Any>): TreeMap<String, Any> {
        for ((k, v) in parms!!) {
            dynamicMap.put(k, v)
        }
        return dynamicMap
    }

    /**
     * 将参数拼接到url中
     *
     * @param url    请求的url
     * @param params 参数
     * @return
     */
    fun createUrlFromParams(url: String, params: Map<String, Any>): String {
        try {
            val sb = StringBuilder()
            sb.append(url)
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0)
                sb.append("&")
            else
                sb.append("?")
            for ((key, value) in params) {
                val urlValues = value.toString()
                //对参数进行 utf-8 编码,防止头信息传中文
                val urlValue = URLEncoder.encode(urlValues, UTF8.name())
                sb.append(key).append("=").append(urlValue).append("&")
            }
            sb.deleteCharAt(sb.length - 1)
            return sb.toString()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return url
    }

    /**
     * 解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
     * 解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
     *
     * @param url
     * @return
     */
    fun parseUrl(url: String): String {
        var url = url
        if ("" != url && url.contains("?")) {// 如果URL不是空字符串
            url = url.substring(0, url.indexOf('?'))
        }
        return url
    }
}
