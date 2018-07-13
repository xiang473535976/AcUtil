package example.aichen.ac

import aichen.green.ww.http.AppService
import com.blankj.utilcode.util.ActivityUtils
import x.aichen.http.RetrofitManager

object X {
    val api = RetrofitManager.init("http://117.78.35.170:9090/jtapi/v1/", ActivityUtils.getTopActivity()).create(AppService::class.java)
    val apiWithUrl2 = RetrofitManager.init("http://117.78.35.170:9090/jtapi/v2/", ActivityUtils.getTopActivity()).create(AppService::class.java)
}