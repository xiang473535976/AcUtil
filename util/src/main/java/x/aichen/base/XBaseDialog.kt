package x.aichen.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ScreenUtils
import x.aichen.R
import x.aichen.extend.longToast
import x.aichen.extend.toast


/**
 * Created by ac on 2017/4/12.
 */

abstract class XBaseDialog protected constructor(styly: Int) : Dialog(ActivityUtils.getTopActivity(), styly) {
    protected abstract val layooutid: Int
    protected abstract fun initView()
    protected lateinit var lp: WindowManager.LayoutParams
    protected lateinit var contentview: View

    init {
        init()
    }

    constructor() : this(R.style.Dialog)

    protected fun st(cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentview = layoutInflater.inflate(layooutid, null)
        setContentView(contentview)
        initView()
    }

    protected fun st(intent: Intent) {
        context.startActivity(intent)
    }

    protected fun init() {
        lp = window.attributes
        lp.alpha = 0.9f
        lp.width = ScreenUtils.getScreenWidth().toInt()
        window.attributes = lp
        // show();
        window.setGravity(Gravity.BOTTOM)  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_inout_anim)  //添加动画
    }

    fun longToast(msg: Any) {
        context?.longToast(msg)
    }

    fun toast(msg: Any) {
        context?.toast(msg)
    }
}
