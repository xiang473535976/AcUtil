package example.aichen.ac

import okhttp3.logging.HttpLoggingInterceptor
import x.aichen.base.XApp
import x.aichen.http.RetrofitManager
import x.aichen.http.interceptor.ParmLogInterceptor

class App : XApp() {
    override fun onCreate() {
        super.onCreate()
        RetrofitManager.CONFIG().interceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .interceptor(ParmLogInterceptor())
                .UseHttpCache(true)



    }
}