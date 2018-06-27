package x.aichen.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.blankj.utilcode.util.KeyboardUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import x.aichen.R


/**
 * author 艾晨
 * Created at 2017/3/25 16:59
 * Update at 2017/3/25 16:59
 * Update people:
 * Version:1.1
 * 说明：
 */

abstract class XBaseActivity : RxAppCompatActivity(), BGASwipeBackHelper.Delegate {
    protected abstract val layooutid: Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initListener()
    protected abstract fun initDate()
    var useEventBus = true //是否启用eventbus
    var addToSmartRefreshLayout: Boolean = false
    var parent_smartrefreshLayout: SmartRefreshLayout? = null
    lateinit var mSwipeBackHelper: BGASwipeBackHelper
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        var view: View? = null
        if (name == LAYOUT_FRAMELAYOUT) {
            view = AutoFrameLayout(context, attrs)
        }

        if (name == LAYOUT_LINEARLAYOUT) {
            view = AutoLinearLayout(context, attrs)
        }

        if (name == LAYOUT_RELATIVELAYOUT) {
            view = AutoRelativeLayout(context, attrs)
        }
        if (view != null) return view

        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSetContentView()
        super.onCreate(savedInstanceState)
        if (addToSmartRefreshLayout)
            toAddSmartRefreshLayout()
        else
            setContentView(layooutid)
        initView(savedInstanceState)
        initAfterViewPrepared()
        initListener()
        initDate()
    }

    open fun initBeforeSetContentView() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
    }

    open fun initAfterViewPrepared() {
        if (useEventBus) EventBus.getDefault().register(this)
    }


    override fun onDestroy() {
        KeyboardUtils.fixSoftInputLeaks(this)  //修复键盘内存泄漏的     无用
        if (useEventBus) EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * 实现刷新功能
     */
    private fun toAddSmartRefreshLayout() {
        parent_smartrefreshLayout = SmartRefreshLayout(this)
        layoutInflater.inflate(layooutid, parent_smartrefreshLayout, true)
        setContentView(parent_smartrefreshLayout)
    }

    companion object {
        //生命周期管理
        private val LAYOUT_LINEARLAYOUT = "LinearLayout"
        private val LAYOUT_FRAMELAYOUT = "FrameLayout"
        private val LAYOUT_RELATIVELAYOUT = "RelativeLayout"
    }

    //暂时屏蔽侧滑
    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

    /**
     * eventbus  必须一个接收器     默认随便写了个哈
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {/* Do something */
    }
}
