package example.aichen.ac

import aichen.green.ww.http.AppService
import x.aichen.http.RetrofitManager

object X {
    val api = RetrofitManager.init("http://117.78.35.170:9090/jtapi/v1/").create(AppService::class.java)
}