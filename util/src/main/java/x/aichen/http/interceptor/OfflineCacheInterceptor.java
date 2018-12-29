package x.aichen.http.interceptor;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import x.aichen.http.config.DefaultConfig;

/**
 * @Description: 离线缓存拦截
 */
public class OfflineCacheInterceptor implements Interceptor {
    private String cacheControlValue;

    public OfflineCacheInterceptor() {
        this(DefaultConfig.MAX_AGE_OFFLINE);
    }

    /**
     * @param cacheControlValue 默认最大离线缓存时间（秒）
     */
    public OfflineCacheInterceptor(int cacheControlValue) {
        this.cacheControlValue = String.format("max-stale=%d", cacheControlValue);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, " + cacheControlValue)
                    .removeHeader("Pragma")
                    .build();
        }
        return chain.proceed(request);
    }
}
