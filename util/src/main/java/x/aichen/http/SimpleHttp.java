package x.aichen.http;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import x.aichen.http.config.HttpGlobalConfig;

/**
 * @Description: 网络请求入口
 */
public class SimpleHttp {
    private static Context context;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;

    private static final HttpGlobalConfig NET_GLOBAL_CONFIG = HttpGlobalConfig.getInstance();

    public static HttpGlobalConfig CONFIG() {
        return NET_GLOBAL_CONFIG;
    }

    public static void init(Context appContext) {
        if (context == null && appContext != null) {
            context = appContext.getApplicationContext();
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
        }
    }

    public static Context getContext() {
        if (context == null) {
            throw new IllegalStateException("Please call SimpleHttp.init(this) in Application to initialize!");
        }
        return context;
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (okHttpBuilder == null) {
            throw new IllegalStateException("Please call SimpleHttp.init(this) in Application to initialize!");
        }
        return okHttpBuilder;
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            throw new IllegalStateException("Please call SimpleHttp.init(this) in Application to initialize!");
        }
        return retrofitBuilder;
    }


    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build();
        }
        return okHttpClient;
    }


}
