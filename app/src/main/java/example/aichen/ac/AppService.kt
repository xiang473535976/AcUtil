package aichen.green.ww.http

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Contract->Model->Presenter->Activity->Module->Component
 * author 艾晨
 * Created at 2017/8/25 15:00
 * Update at 2017/8/25 15:00
 * Update people:
 * Version:1.0
 * 说明：常用app接口
 */

interface AppService {


    /**
     *用户登录
     */
    @FormUrlEncoded
    @POST("ulogin")
    fun ulogin(@Field("login_name") login_name: String, @Field("login_pwd") login_pwd: String): Observable<Any>


}