package x.aichen.extend

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import x.aichen.base.XBaseActivity
import java.io.File


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
    io.reactivex.Flowable.just(paths)
            .observeOn(io.reactivex.schedulers.Schedulers.io())
            .map {
                //传人图片  压缩   ->  list<File>
                top.zibin.luban.Luban.with(this).load(it).get()
            }
            .map { listfile ->
                //  将 list<File>  ->list<String>
                kotlin.collections.arrayListOf<String>().apply { listfile.forEach { add(it.toString()) } }
            }
            .bindToLifecycle(this)
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                block(it)
                pro.dismiss()
            }
}