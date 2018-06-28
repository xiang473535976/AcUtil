package x.aichen.adapter

/**
 * Created by 艾晨 on 2017/9/14.
 */

import android.view.ViewGroup

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zhy.autolayout.utils.AutoUtils

import java.util.ArrayList

/**
 * 继承BaseMultiItemQuickAdapter的一个适配器基类
 */
abstract class BaseMultiItemAdapter<T : MultiItemEntity> : BaseMultiItemQuickAdapter<T, BaseViewHolder> {
    private var listData: ArrayList<T>? = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        AutoUtils.autoSize(viewHolder.itemView) //屏幕适配
        return viewHolder
    }

    constructor(data: ArrayList<T>?) : super(data) {
        listData = data
    }
}