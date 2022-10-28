package x.aichen.extend

import android.annotation.SuppressLint
import android.content.Context
import com.blankj.utilcode.util.FileUtils
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import x.aichen.base.XBaseActivity


/**
 * 创建对应目录到android缓存目录下
 *
 * @return
 */
fun Context.createCacheFile(childrenName: String): String {
    var cache = applicationContext.externalCacheDir
    val path = "$cache/$childrenName"
    FileUtils.createOrExistsDir(path)
    return path
}

@SuppressLint("CheckResult")
        /**
         * luban压缩图片
         */
fun XBaseActivity.toLuban(paths: List<String>, block: (lis: ArrayList<String>) -> Unit) {
    val pro = showProgressDialog()
    pro.show()
    Flowable.just(paths)
            .observeOn(Schedulers.io())
            .map {
                //传人图片  压缩   ->  list<File>
                top.zibin.luban.Luban.with(this).load(it).get()
            }
            .map { listfile ->
                //  将 list<File>  ->list<String>
                kotlin.collections.arrayListOf<String>().apply { listfile.forEach { add(it.toString()) } }
            }
            .bindToLifecycle(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                block(it)
                pro.dismiss()
            }
}