package x.aichen.http

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * author 艾晨
 * Created at 2017/3/25 15:01
 * Update at 2017/3/25 15:01
 * Update people:
 * Version:1.1
 * 说明：错误处理
 */
class ErrorTips {


    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 禁止访问
         */
        val FORBID_ACCESS = 100
        /**
         * IP禁止访问
         */
        val FORBID_ACCESS_IP = 400

        /**
         * 账号不存在
         */
        val ACCOUNT_NOT_EXIST = 500
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORD_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = 1006

        /**
         * 网络错误
         */
        val NET_ERROR = 1007
    }

    class ResponeThrowable(throwable: Throwable, var code: Int) : Exception(throwable) {
        var msg: String? = null
    }

    inner class ServerException : RuntimeException() {
        var code: Int = 0
    }

    class CodeException(val errCode: Int, val msg: String) : RuntimeException(msg)

    companion object {

        private val UNAUTHORIZED = 401
        private val FORBIDDEN = 403
        private val NOT_FOUND = 404
        private val REQUEST_TIMEOUT = 408
        private val INTERNAL_SERVER_ERROR = 500
        private val BAD_GATEWAY = 502
        private val SERVICE_UNAVAILABLE = 503
        private val GATEWAY_TIMEOUT = 504

        fun handleException(e: Throwable): ResponeThrowable {
            var ex: ResponeThrowable
            if (e is HttpException) {
                ex = ResponeThrowable(e, ERROR.HTTP_ERROR)
                when (e.code()) {
                    UNAUTHORIZED -> {
                        //401  登录失效
                        ToastUtils.showShort("请登录")
                    }
                    FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.msg = "网络错误"
                    else -> ex.msg = "网络错误"
                }
                return ex
            } else if (e is ServerException) {
                ex = ResponeThrowable(e, e.code)
                ex.msg = e.message
                return ex
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException) {
                ex = ResponeThrowable(e, ERROR.PARSE_ERROR)
                ex.msg = "解析错误"
                return ex
            } else if (e is ConnectException) {
                ex = ResponeThrowable(e, ERROR.NETWORD_ERROR)
                ex.msg = "连接失败"
                return ex
            } else if (e is javax.net.ssl.SSLHandshakeException) {
                ex = ResponeThrowable(e, ERROR.SSL_ERROR)
                ex.msg = "证书验证失败"
                return ex
            } else if (e is ConnectTimeoutException) {
                ex = ResponeThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.msg = "连接超时"
                return ex
            } else if (e is UnknownHostException) {
                ex = ResponeThrowable(e, ERROR.NET_ERROR)
                ex.msg = "网络错误"
                return ex
            } else if (e is java.net.SocketTimeoutException) {
                ex = ResponeThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.msg = "连接超时"
                return ex
            } else if (e is CodeException) {
                ex = ResponeThrowable(e, ERROR.FORBID_ACCESS)
                when (e.errCode) {
                    ERROR.FORBID_ACCESS -> {
                        ex = ResponeThrowable(e, ERROR.FORBID_ACCESS)
                        ex.msg = "禁止访问"
                        ex = ResponeThrowable(e, ERROR.FORBID_ACCESS_IP)
                        ex.msg = "IP禁止访问"
                        ex = ResponeThrowable(e, ERROR.ACCOUNT_NOT_EXIST)
                        ex.msg = "IP禁止访问"
                    }
                    ERROR.FORBID_ACCESS_IP -> {
                        ex = ResponeThrowable(e, ERROR.FORBID_ACCESS_IP)
                        ex.msg = "IP禁止访问"
                        ex = ResponeThrowable(e, ERROR.ACCOUNT_NOT_EXIST)
                        ex.msg = "IP禁止访问"
                    }
                    ERROR.ACCOUNT_NOT_EXIST -> {
                        ex = ResponeThrowable(e, ERROR.ACCOUNT_NOT_EXIST)
                        ex.msg = "IP禁止访问"
                    }
                }
                if (TextUtils.isEmpty(e.msg))
                    ex.msg = e.message
                return ex
            } else {
                ex = ResponeThrowable(e, ERROR.UNKNOWN)
                ex.msg = "未知错误"
                return ex
            }

        }
    }
}