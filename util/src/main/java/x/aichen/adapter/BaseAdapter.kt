package x.aichen.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import x.aichen.extend.LongToast
import x.aichen.extend.Toast

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
        animationEnable = true
        setAnimationWithDefault(AnimationType.AlphaIn)
    }

    constructor(layoutResId: Int) : this(layoutResId, arrayListOf())
    constructor(layoutResId: Int, data: ArrayList<T>?) : super(layoutResId, data)

//    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
//        val viewHolder = super.onCreateDefViewHolder(parent, viewType)
//        if (mContext is XBaseActivity) {
//            (mContext as XBaseActivity).apply {
//                if (useAutolayoutToFitScreen)
//                    AutoUtils.autoSize(viewHolder.itemView, AutoAttr.BASE_HEIGHT) //屏幕适配
//            }
//        }
//        return viewHolder
//    }

    /**
     * 清空所有
     */
    fun clear() {
        this.data!!.clear()
    }

    fun longToast(msg: Any) {
        context?.LongToast(msg)
    }

    fun toast(msg: Any) {
        context?.Toast(msg)
    }
}