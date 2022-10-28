package example.aichen.ac

import com.blankj.utilcode.util.ActivityUtils
import x.aichen.http.RetrofitManager

object X {
    val api = RetrofitManager.init("http://117.78.35.170:9090/jtapi/v1/", ActivityUtils.getTopActivity()).create(
        AppService::class.java)
    val apiWithUrl2 = RetrofitManager.init("https://api.apishop.net/common/", ActivityUtils.getTopActivity()).create(
        AppService::class.java)
}