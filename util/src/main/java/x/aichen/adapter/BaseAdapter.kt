package x.aichen.adapter

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.autolayout.attr.AutoAttr
import com.zhy.autolayout.utils.AutoUtils
import x.aichen.base.XBaseActivity
import x.aichen.extend.longToast
import x.aichen.extend.toast

/**
 * Contract->Model->Presenter->Activity->Module->Component
 * author 艾晨
 * Created at 2017/6/9 17:04
 * Update at 2017/6/9 17:04
 * Update people:
 * Version:1.0
 * 说明：基类adapter
 */

abstract class BaseAdapter<T> : BaseQuickAdapter<T, BaseViewHolder> {
    init {
        openLoadAnimation(BaseQuickAdapter.ALPHAIN)  //开启列表动画
//        openLoadAnimation(BaseQuickAdapter.SCALEIN)  //开启列表动画
        isFirstOnly(true)
    }

    constructor(layoutResId: Int) : this(layoutResId, arrayListOf())
    constructor(layoutResId: Int, data: ArrayList<T>?) : super(layoutResId, data)

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val viewHolder = super.onCreateDefViewHolder(parent, viewType)
        if (mContext is XBaseActivity) {
            (mContext as XBaseActivity).apply {
                if (useAutolayoutToFitScreen)
                    AutoUtils.autoSize(viewHolder.itemView, AutoAttr.BASE_HEIGHT) //屏幕适配
            }
        }
        return viewHolder
    }

    /**
     * 清空所有
     */
    fun clear() {
        this.mData!!.clear()
        notifyDataSetChanged()
    }

    fun longToast(msg: Any) {
        mContext?.longToast(msg)
    }

    fun toast(msg: Any) {
        mContext?.toast(msg)
    }
}