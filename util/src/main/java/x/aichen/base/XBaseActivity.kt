package x.aichen.base

import android.os.Bundle
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * author 艾晨
 * Created at 2017/3/25 16:59
 * Update at 2017/3/25 16:59
 * Update people:
 * Version:1.1
 * 说明：
 */

abstract class XBaseActivity : RxAppCompatActivity() {
    protected abstract val layooutid: Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initListener()
    protected abstract fun initDate()
    var useEventBus = true //是否启用eventbus
    var addToSmartRefreshLayout: Boolean = false
    var parent_smartrefreshLayout: SmartRefreshLayout? = null
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

    }

    open fun initAfterViewPrepared() {
        if (useEventBus) EventBus.getDefault().register(this)
    }


    override fun onDestroy() {
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

    /**
     * eventbus  必须一个接收器     默认随便写了个哈
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Char) {/* Do something */
    }
}
