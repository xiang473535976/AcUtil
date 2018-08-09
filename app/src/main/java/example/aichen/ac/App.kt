package example.aichen.ac

import okhttp3.logging.HttpLoggingInterceptor
import x.aichen.base.XApp
import x.aichen.http.RetrofitManager
import x.aichen.http.interceptor.ParmInterceptor
import x.aichen.http.interceptor.LogParmInterceptor

class App : XApp() {

    override fun onCreate() {
        super.onCreate()
        val parm= hashMapOf<String,Any>()
        parm.put("parm1",2)
        parm.put("parm2","向")
        RetrofitManager.CONFIG().interceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .interceptor(ParmInterceptor(parm))
                .interceptor(LogParmInterceptor())
                .UseHttpCache(true)


    }
}