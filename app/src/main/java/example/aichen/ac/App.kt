package example.aichen.ac

import okhttp3.logging.HttpLoggingInterceptor
import x.aichen.base.XApp
import x.aichen.http.SimpleHttp

class App : XApp() {
    override fun onCreate() {
        super.onCreate()
        SimpleHttp.init(this)
        SimpleHttp.CONFIG().interceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
}