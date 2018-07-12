package x.aichen.http

import okhttp3.ConnectionPool
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import x.aichen.http.config.SimpleConfig
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * author 艾晨
 * Created at 2018/7/12 17:17
 * Update at 2018/7/12 17:17
 * Update people:
 * Version:1.0
 * 说明： retrofit的简单封装
 */

//不能用单例object   否则没法改变BASE_URL
object RetrofitManager {
    private var baseUrl: String? = null//基本的访问防止

    /**
     * *
     * @param base_URL    //基础域名
     */
    fun init(base_URL: String): Retrofit {
        baseUrl = base_URL
        generateGlobalConfig()
        return SimpleHttp.getRetrofitBuilder()
                .client(SimpleHttp.getOkHttpClient())
                .build()
    }


    /**
     * 生成全局配置
     */
    private fun generateGlobalConfig() {
        val httpGlobalConfig = SimpleHttp.CONFIG()
        SimpleHttp.getRetrofitBuilder().baseUrl(baseUrl)
        if (httpGlobalConfig.converterFactory != null) {
            SimpleHttp.getRetrofitBuilder().addConverterFactory(httpGlobalConfig.converterFactory)
        }

        if (httpGlobalConfig.callAdapterFactory == null) {
            httpGlobalConfig.callAdapterFactory(RxJava2CallAdapterFactory.create())
        }
        SimpleHttp.getRetrofitBuilder().addCallAdapterFactory(httpGlobalConfig.callAdapterFactory)

        if (httpGlobalConfig.callFactory != null) {
            SimpleHttp.getRetrofitBuilder().callFactory(httpGlobalConfig.callFactory)
        }
        if (httpGlobalConfig.converterFactory != null) {
            SimpleHttp.getRetrofitBuilder().addConverterFactory(httpGlobalConfig.converterFactory)
        } else
            SimpleHttp.getRetrofitBuilder().addConverterFactory(GsonConverterFactory.create())



        if (httpGlobalConfig.connectionPool == null) {
            httpGlobalConfig.connectionPool(ConnectionPool(SimpleConfig.DEFAULT_MAX_IDLE_CONNECTIONS,
                    SimpleConfig.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS))
        }
        SimpleHttp.getOkHttpBuilder().connectionPool(httpGlobalConfig.connectionPool)



        if (httpGlobalConfig.httpCacheDirectory == null) {
            httpGlobalConfig.httpCacheDirectory = File(SimpleHttp.getContext().cacheDir, SimpleConfig.CACHE_HTTP_DIR)
        }
    }
}





