package x.aichen.http.config;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call.Factory;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import x.aichen.http.RetrofitManager;
import x.aichen.http.interceptor.OfflineCacheInterceptor;
import x.aichen.http.interceptor.OnlineCacheInterceptor;

/**
 * @Description: 请求全局配置
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 17:17
 */
public class HttpGlobalConfig {
    private CallAdapter.Factory callAdapterFactory;//Call适配器工厂
    private Converter.Factory converterFactory;//转换工厂
    private Factory callFactory;//Call工厂
    private ConnectionPool connectionPool;//连接池
    private File httpCacheDirectory;//Http缓存路径
    private int retryDelayMillis;//请求失败重试间隔时间
    private int retryCount;//请求失败重试次数
    private Long CACHE_MAX_SIZE ;  //缓存的大小
    private boolean isHttpCache;//是否使用Http缓存
    private Cache httpCache;//Http缓存对象

    /**
     * 设置请求失败重试间隔时间
     *
     * @param retryDelayMillis
     * @return
     */
    public HttpGlobalConfig retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    /**
     * 设置请求失败重试次数
     *
     * @param retryCount
     * @return
     */
    public HttpGlobalConfig retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }


    /**
     * 设置HTTP缓存
     *
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig httpCache(Cache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    public boolean isHttpCache() {
        return isHttpCache;
    }

    /**
     * 设置是否添加HTTP缓存
     *
     * @param isHttpCache
     * @return
     */
    public HttpGlobalConfig UseHttpCache(boolean isHttpCache) {
        this.isHttpCache = isHttpCache;
        return this;
    }

    public Long getCacheMaxSize() {
        return CACHE_MAX_SIZE;
    }

    public HttpGlobalConfig CacheMaxSize(Long cache_max_size) {
        this.CACHE_MAX_SIZE = cache_max_size;
        return this;
    }

    private static HttpGlobalConfig instance;

    private HttpGlobalConfig() {
    }

    public static HttpGlobalConfig getInstance() {
        if (instance == null) {
            synchronized (HttpGlobalConfig.class) {
                if (instance == null) {
                    instance = new HttpGlobalConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 设置CallAdapter工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig callAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    /**
     * 设置转换工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig converterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 设置Call的工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig callFactory(Factory factory) {
        this.callFactory = checkNotNull(factory, "factory == null");
        return this;
    }


    /**
     * 设置连接池
     *
     * @param connectionPool
     * @return
     */
    public HttpGlobalConfig connectionPool(ConnectionPool connectionPool) {
        this.connectionPool = checkNotNull(connectionPool, "connectionPool == null");
        return this;
    }


    /**
     * 设置HTTP缓存路径
     *
     * @param httpCacheDirectory
     * @return
     */
    public HttpGlobalConfig httpCacheDirectory(File httpCacheDirectory) {
        this.httpCacheDirectory = httpCacheDirectory;
        return this;
    }


    /**
     * 设置代理
     *
     * @param proxy
     * @return
     */
    public HttpGlobalConfig proxy(Proxy proxy) {
        RetrofitManager.getOkHttpBuilder2().proxy(checkNotNull(proxy, "proxy == null"));
        return this;
    }

    /**
     * 设置连接超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig connectTimeout(int timeout) {
        return connectTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置读取超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig readTimeout(int timeout) {
        return readTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置写入超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig writeTimeout(int timeout) {
        return writeTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置连接超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig connectTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            RetrofitManager.getOkHttpBuilder2().connectTimeout(timeout, unit);
        } else {
            RetrofitManager.getOkHttpBuilder2().connectTimeout(DefaultConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig writeTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            RetrofitManager.getOkHttpBuilder2().writeTimeout(timeout, unit);
        } else {
            RetrofitManager.getOkHttpBuilder2().writeTimeout(DefaultConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig readTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            RetrofitManager.getOkHttpBuilder2().readTimeout(timeout, unit);
        } else {
            RetrofitManager.getOkHttpBuilder2().readTimeout(DefaultConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置拦截器
     *
     * @param interceptor
     * @return
     */
    public HttpGlobalConfig interceptor(Interceptor interceptor) {
        RetrofitManager.getOkHttpBuilder2().addInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置网络拦截器
     *
     * @param interceptor
     * @return
     */
    public HttpGlobalConfig networkInterceptor(Interceptor interceptor) {
        RetrofitManager.getOkHttpBuilder2().addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig cacheOnline(Cache httpCache) {
        networkInterceptor(new OnlineCacheInterceptor());
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig cacheOffline(Cache httpCache) {
        networkInterceptor(new OfflineCacheInterceptor());
        interceptor(new OfflineCacheInterceptor());
        this.httpCache = httpCache;
        return this;
    }

    public CallAdapter.Factory getCallAdapterFactory() {
        return callAdapterFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public Factory getCallFactory() {
        return callFactory;
    }


    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public int getRetryDelayMillis() {
        if (retryDelayMillis <= 0) {
            retryDelayMillis = DefaultConfig.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    public Cache getHttpCache() {
        return httpCache;
    }

    public int getRetryCount() {
        if (retryCount <= 0) {
            retryCount = DefaultConfig.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    public File getHttpCacheDirectory() {
        return httpCacheDirectory;
    }

    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
