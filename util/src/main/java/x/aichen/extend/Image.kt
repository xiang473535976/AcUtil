package x.aichen.extend

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import x.aichen.R

/**
 * 图片下载
 */

fun ImageView.GlideImage(url: String?) {
    GlideImage(url, 0)
}

fun ImageView.GlideImageNoAnim(url: String?) {
    GlideImage(url, 0, 0, 0, null, false)
}

/**
 * 加入特殊配置
 */
fun ImageView.GlideImage(url: String?, requestOptions: RequestOptions) {
    Glide.with(context)
            .load(url)
            .thumbnail(0.1f)
            .apply(requestOptions)
            .into(this)

}


fun ImageView.GlideImage(url: String?, errordrawable: Int) {
    GlideImage(url, 0, 0, errordrawable, null, true)
}

fun ImageView.GlideImage(url: String?, errordrawable: Int, listener: RequestListener<Drawable>) {
    GlideImage(url, 0, 0, errordrawable, listener, true)
}

/**
 * 传入尺寸   可以避免图片过大  造成oom
 */
fun ImageView.GlideImage(url: String?, width: Int, height: Int) {
    GlideImage(url, width, height, 0, null, true)
}

/**
 *总配置
 */
fun ImageView.GlideImage(url: String?, width: Int = 0, height: Int = 0, errordrawable: Int = 0,
                         listener: RequestListener<Drawable>? = null, anim: Boolean) {
    if (null == url) {
        return
    }
    if (null == context)
        print(Exception("context不存在了!"))
    else {
        val opt = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//默认缓存策略。根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
        //   .placeholder(android.R.color.transparent)  eg https://blog.csdn.net/w6718189/article/details/75085731
        if (0 != errordrawable)
            opt.error(errordrawable)
        else
            opt.error(R.drawable.ic_nothing)
        if (0 != width && 0 != height)
            opt.override(width, height)
        val builder = Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(opt)
        if (anim) builder.transition(DrawableTransitionOptions.withCrossFade(500))   //淡入动画 500
        listener?.let { builder.listener(listener) }
        builder.into(this)
    }
}

/**
 * 下载图片
 */
fun Context.loadBitmap(url: String?, width: Int = 0, height: Int = 0, onLoadListener: (bitmap: Bitmap) -> Unit) {
    val opt = RequestOptions()
    if (0 != width && 0 != height)
        opt.override(width, height)
    Glide.with(this).asBitmap().apply(opt).load(url).into(object : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            resource?.let { onLoadListener(resource!!) }
        }

    })
}

/**
 * 取消加载
 */
fun ImageView.loadClear(context: Context) {
    Glide.with(context).clear(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(path: String) {
    var options = getOptions()
    options.circleCrop()
    Glide.with(context).load(path).apply(options).into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundCornerImage(path: String, roundingRadius: Int = 20) {
    var options = getOptions()
    Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius))).apply(options).into(this)
}

private fun ImageView.getOptions(): RequestOptions {
    var options = RequestOptions()
    options.priority(Priority.HIGH)
    options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    return options
}

