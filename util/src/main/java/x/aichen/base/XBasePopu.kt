package x.aichen.base

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.widget.PopupWindow
import com.blankj.utilcode.util.ActivityUtils


/**
 * Created by ac on 2017/4/12.
 * 基类popuwindow  监听返回   点击空白   假阴影 消失
 */

abstract class XBasePopu : PopupWindow() {
    protected abstract val layooutid: Int
    protected abstract fun initView()
    protected val mcontext: Context by lazy { ActivityUtils.getTopActivity() }

    init {
        contentView = LayoutInflater.from(mcontext).inflate(layooutid, null)
        width = WindowManager.LayoutParams.MATCH_PARENT //为了适配android7.0   设置宽度全屏   所以布局的时候   就宽度全屏 并且放好popuwindow的位置
        height = WindowManager.LayoutParams.WRAP_CONTENT
//        animationStyle = R.style.popu_inout_anim
//        isOutsideTouchable = false
        setBackgroundDrawable(ColorDrawable(0))// 响应返回键必须的语句。
//        val dismiss_view = fd<View>(R.id.dismiss_view)
//        dismiss_view.click { dismiss() }
        isFocusable = true// 设置view能够接听事件
        contentView.isFocusableInTouchMode = true// 设置view能够接听事件
        //监听Back返回键
        contentView.setOnKeyListener { _, code, _ ->
            if (code == KeyEvent.KEYCODE_BACK)
                if (isShowing)
                    dismiss()
            false
        }
        initView()
        setOnDismissListener {
            backgroundAlpha(1f)
        }


    }


    protected fun st(cls: Class<*>) {
        val intent = Intent(mcontext, cls)
        mcontext.startActivity(intent)
    }

    protected fun <T : View> fd(_id: Int): T = contentView.findViewById(_id)
    /**
     * 华为某些手机失效  7.0   测试发现在7.0以下和7.1系统下都没有类似问题。
     */
    override fun showAsDropDown(view: View?) {
        if (Build.VERSION.SDK_INT == 24) {
            val location = IntArray(2)
            view!!.getLocationOnScreen(location)
//            val x = location[0]
            val y = location[1]
            showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.height)
        } else {
            super.showAsDropDown(view)
        }
        backgroundAlpha(0.9f)
    }

    // 设置屏幕透明度
    private fun backgroundAlpha(bgAlpha: Float) {
        val lp = ActivityUtils.getTopActivity().window.attributes
        lp.alpha = bgAlpha // 0.0~1.0
        ActivityUtils.getTopActivity().window.attributes = lp //act 是上下文context

    }
}
