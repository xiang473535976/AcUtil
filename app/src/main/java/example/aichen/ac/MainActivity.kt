package example.aichen.ac

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import x.aichen.base.XBaseActivity
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
            pick.start()
        }
    }

    override fun initDate() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pick.onActivityResult(this, null, requestCode, resultCode, data)
    }

}

