package x.aichen.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle4.components.support.RxFragment


abstract class XFragment : RxFragment() {
    protected abstract val layoutResId: Int
    lateinit var contentView: View
    protected abstract fun initView(savedInstanceState: Bundle?)
    /*** 懒加载数据*/
    protected abstract fun lazyLoad()

    /**
     * 是否可见状态 为了避免和[Fragment.isVisible]冲突 换个名字
     */
    protected var isFragmentVisible: Boolean = false

    /**
     * 第一次加载成功
     * 注意   需要在请求成功后    改
     * isFirstLoadSuccess=true
     * 如果需要强制刷新   也可改变这个参数
     * isFirstLoadSuccess=false   等会Fragment可见时  自动执行刷新
     */
    protected var isFirstLoadSuccess = false
    protected var addToSmartRefreshLayout: Boolean = false
    protected abstract fun initListener()
    protected val smartRefreshLayout: SmartRefreshLayout by lazy { SmartRefreshLayout(context) }
    protected var isPrepared: Boolean = false//是否初始化完成

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initBeforeSetContentView()
        if (addToSmartRefreshLayout)
            addToSmartRefreshLayout(inflater)
        else
            contentView = inflater.inflate(layoutResId, container, false)
        return contentView
    }

    open fun initBeforeSetContentView() {

    }


    /**
     * 实现刷新功能
     */
    private fun addToSmartRefreshLayout(inflater: LayoutInflater) {
        smartRefreshLayout.setRefreshContent(inflater.inflate(layoutResId, null))
        contentView = smartRefreshLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initListener()
        isPrepared = true
        lazyLoad()
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not visible.
     * 需要先hide再show
     * 需要先hide再show
     * 需要先hide再show
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    private fun onVisible() {
        isFragmentVisible = true
        loadDate()
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    private fun loadDate() {
        if (isPrepared && isFragmentVisible) {
            if (!isFirstLoadSuccess) {
                lazyLoad()
            }
        }
    }

    private fun onInvisible() {
        isFragmentVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
    }

}
