package x.aichen.http

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.NonNull
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import x.aichen.http.config.HttpGlobalConfig
import x.aichen.http.config.SimpleConfig
import java.io.File
import java.util.concurrent.TimeUnit


@SuppressLint("StaticFieldLeak")
/**
 * author 艾晨
 * Created at 2018/7/12 17:17
 * Update at 2018/7/12 17:17
 * Update people:
 * Version:1.0
 * 说明： retrofit的简单封装
 */
object RetrofitManager {
    private var baseUrl: String? = null//基本的访问防止
    private val okHttpBuilder by lazy { OkHttpClient.Builder() }
    private val retrofitBuilder by lazy { Retrofit.Builder() }
    private val okHttpClient by lazy { okHttpBuilder.build() }
    private val httpGlobalConfig by lazy { HttpGlobalConfig.getInstance() }
    private var context: Context? = null

    @JvmStatic
    fun getOkHttpBuilder2(): OkHttpClient.Builder {
        return okHttpBuilder
    }

    @JvmStatic
    fun getRetrofitBuilder2(): Retrofit.Builder {
        return retrofitBuilder
    }

    @JvmStatic
    fun getOkHttpClient2(): OkHttpClient {
        return okHttpClient
    }

    fun CONFIG(): HttpGlobalConfig {
        return httpGlobalConfig
    }

    /**
     * *
     * @param base_URL    //基础域名
     */
    fun init(@NonNull base_URL: String, @NonNull con: Context): Retrofit {
        baseUrl = base_URL
        context = con.applicationContext
        generateGlobalConfig()
        return retrofitBuilder
                .client(okHttpClient)
                .build()
    }


    /**
     * 生成全局配置
     */
    private fun generateGlobalConfig() {
        retrofitBuilder.baseUrl(baseUrl)
        if (httpGlobalConfig.converterFactory != null) {
            retrofitBuilder.addConverterFactory(httpGlobalConfig.converterFactory)
        }

        if (httpGlobalConfig.callAdapterFactory == null) {
            httpGlobalConfig.callAdapterFactory(RxJava2CallAdapterFactory.create())
        }
        retrofitBuilder.addCallAdapterFactory(httpGlobalConfig.callAdapterFactory)

        if (httpGlobalConfig.callFactory != null) {
            retrofitBuilder.callFactory(httpGlobalConfig.callFactory)
        }
        if (httpGlobalConfig.converterFactory != null) {
            retrofitBuilder.addConverterFactory(httpGlobalConfig.converterFactory)
        } else
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())



        if (httpGlobalConfig.connectionPool == null) {
            httpGlobalConfig.connectionPool(ConnectionPool(SimpleConfig.DEFAULT_MAX_IDLE_CONNECTIONS,
                    SimpleConfig.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS))
        }
        okHttpBuilder.connectionPool(httpGlobalConfig.connectionPool)

        if (httpGlobalConfig.httpCacheDirectory == null) {
            httpGlobalConfig.httpCacheDirectory = File(context!!.cacheDir, SimpleConfig.CACHE_HTTP_DIR)
        }
        if (httpGlobalConfig.cachE_MAX_SIZE == 0L) {
            httpGlobalConfig.cachE_MAX_SIZE = SimpleConfig.CACHE_MAX_SIZE
        }
        okHttpBuilder.cache(Cache(httpGlobalConfig.httpCacheDirectory, httpGlobalConfig.cachE_MAX_SIZE))
    }
}





