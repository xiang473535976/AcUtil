package example.aichen.ac

import aichen.green.ww.http.ABaseSubscriber
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import x.aichen.base.XBaseActivity
import x.aichen.extend.io_main_bindLife
import x.aichen.http.bean.Dto
import x.aichen.picker.ImagePick
import x.aichen.picker.PickBuilder


class MainActivity : XBaseActivity() {
    private val pick by lazy {
        ImagePick.builder(this, null, PickBuilder().setCrop(true).setCompress(true).setmMaxSelectable(1)) {

        }
    }
    override val layooutid: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        toCick.setOnClickListener {
            pick.toMatisse()
        }
        toNet.setOnClickListener {
            toHttp()
        }
        toNetChangeUrl.setOnClickListener {
            netChangeUrl()
        }
    }

    private fun netChangeUrl() {
        X.apiWithUrl2.ulogin2("13000000000", "123456")
                .io_main_bindLife(this)
                .subscribe({
                    LogUtils.e(it)
                }, {

                })
    }

    private fun toHttp() {
        X.api.ulogin("13000000000", "123456")
                .io_main_bindLife(this)
                .subscribe(object : ABaseSubscriber<Dto<Any>>(true) {
                    override fun onSuccess(t: Dto<Any>) {

                    }

                })
    }

    override fun initDate() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pick.onActivityResult(this, null, requestCode, resultCode, data)
    }

}

