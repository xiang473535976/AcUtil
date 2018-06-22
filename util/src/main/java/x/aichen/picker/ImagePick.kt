package x.aichen.picker

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.LogUtils
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.zibin.luban.Luban
import x.aichen.util.Util
import java.util.*
import kotlin.collections.ArrayList


//@SuppressLint("StaticFieldLeak")
/**
 * 使用教程
 * 1.在manifests中添加下面代码
 * 2.混淆规则中 添加 luban   crop  Matisse对应混淆规则
 * 3  在使用的act中回调 onActivityResult
 * 4.Enjoy your  self
 */
//<activity
//android:name="com.yalantis.ucrop.UCropActivity"
//android:screenOrientation="portrait"
//android:theme="@style/Theme.AppCompat.Light.NoActionBar" />
//<provider
//android:name="android.support.v4.content.FileProvider"
//android:authorities="${applicationId}.fileprovider"
//android:exported="false"
//android:grantUriPermissions="true">
//<meta-data
//android:name="android.support.FILE_PROVIDER_PATHS"
//android:resource="@xml/provider_paths" />
//</provider>

object ImagePick {
    private var REQUEST_CODE_SELECT: Int = 110
    private var pickBuilder: PickBuilder? = null
    private var onPickResultListener: onPickResultListener? = null
    /**
     * 剪切多图
     */
    private var nowCropUri: Uri? = null  //当前正在剪切的图片的地址    主要是剪切考虑到剪切取消的情况
    private var cropedsList: ArrayList<Uri>? = null  //已经剪切了的图片的地址
    private var mSelectedUri: List<Uri>? = null    //选取的图片的地址
    /**
     * activity  fragment     两个中至少有一个不为空
     * pickBuilder     配置构造器  （里面已经包含了一些常用基本信息）
     * onSelectListener    回调监听器
     *   初始化  选图配置
     */
    fun builder(activity: Activity?, fragment: Fragment?, pickBuilder: PickBuilder, onPickResultListener: onPickResultListener) {
        ImagePick.pickBuilder = pickBuilder
        ImagePick.onPickResultListener = onPickResultListener
        with(pickBuilder) {
            if (getmMimeType().contains(MimeType.GIF) || getmMimeType().containsAll(MimeType.ofVideo())) { //这些类型  视频   gif   不支持压缩和裁剪
                isCompress = false
                isCrop = false
            }
//            if (isCrop)
//                setmMaxSelectable(1)  //剪切图片 就只选取一张
            var matisse = if (null != activity)
                Matisse.from(activity)
            else
                Matisse.from(fragment)
            matisse.choose(getmMimeType())
                    .apply {
                        countable(ismCountable())
                        maxSelectable(getmMaxSelectable())
                        getmFilters()?.forEach { addFilter(it) }
                        gridExpectedSize(getmGridExpectedSize())
                        capture(ismCapture())
                        captureStrategy(getmCaptureStrategy())
                        restrictOrientation(getmOrientation())
                        thumbnailScale(getmThumbnailScale())
                        imageEngine(getmImageEngine()).forResult(REQUEST_CODE_SELECT)
                    }

        }

    }

    /**
     * 回调
     * 再次传入 避免内存泄漏
     */
    fun onActivityResult(activity: Activity?, fragment: Fragment?, requestCode: Int, resultCode: Int, data: Intent?) {
        var context = activity ?: fragment!!.context
        when (resultCode) {
            RESULT_OK -> when (requestCode) {
                REQUEST_CODE_SELECT -> {  //调用Matisse 选择的响应
                    mSelectedUri = Matisse.obtainResult(data)
                    if (pickBuilder!!.isCrop) {
                        cropedsList = arrayListOf()
                        startToCropPhoto(activity, fragment)
                    } else
                        toCompressByLuBan(context)
                }
                UCrop.REQUEST_CROP -> {//UCrop  图片剪切的响应
                    cropedsList?.add(UCrop.getOutput(data!!)!!)
                    if (needContainerCrop())
                        startToCropPhoto(activity, fragment)
                    else
                        toCompressByLuBan(context)

//                    handleResult(context!!, arrayListOf<Uri>().apply { add(UCrop.getOutput(data!!)!!) })
                }
            }
            UCrop.RESULT_ERROR -> { //UCrop  图片剪切失败的响应
                LogUtils.e("cropError  " + UCrop.getError(data!!))
            }
            RESULT_CANCELED -> {
                when (requestCode) {
                    UCrop.REQUEST_CROP -> {//UCrop  取消剪切的响应
                        cropedsList?.add(nowCropUri!!)
                        if (needContainerCrop())
                            startToCropPhoto(activity, fragment)
                        else
                            toCompressByLuBan(context)
                    }
                }


            }
        }
    }

    /**
     * 处理      判断是否压缩
     */
    @SuppressLint("CheckResult")
    private fun toCompressByLuBan(context: Context?) {
        if (pickBuilder!!.isCrop) mSelectedUri = cropedsList   //如果剪切了   就处理剪切后的集合
        val paths = arrayListOf<String>().apply { mSelectedUri?.forEach { add(Util.getRealFilePath(context!!, it)!!) } }
        if (pickBuilder!!.isCompress) {
            Flowable.just(paths)
                    .observeOn(Schedulers.io())
                    .map {
                        //传人图片  压缩   ->  list<File>
                        Luban.with(context).load(it).get()
                    }
                    .map {
                        //  将 list<File>  ->list<String>
                        arrayListOf<String>().apply { it.forEach { add(it.toString()) } }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        callPickedBackAndRealse(it)
                    }
        } else {
            callPickedBackAndRealse(paths)
        }

    }

    /**
     * 执行回调
     */
    private fun callPickedBackAndRealse(resultPaths: ArrayList<String>) {
        resultPaths.forEach {
            //打印下选择的地址
            LogUtils.e(it)
        }
        //回调
        onPickResultListener!!.onPicked(resultPaths)
        //施放资源
        onPickResultListener = null
        mSelectedUri = null
        cropedsList = null
        nowCropUri = null
    }

    /**
     * 图片剪切
     */
    private fun startToCropPhoto(activity: Activity?, fragment: Fragment?) {
        nowCropUri = mSelectedUri?.get(cropedsList!!.size)
        var context = activity ?: fragment!!.context
        val destUri = Uri.Builder()
                .scheme("file")
                .appendPath(Util.CreateCacheFile(context!!, "crop")!!.toString())
                .appendPath(String.format(Locale.CHINA, "%s.jpg", System.currentTimeMillis()))
                .build()
        val bu = UCrop.of(nowCropUri!!, destUri)
        pickBuilder?.apply {
            cropOptions?.let { bu.withOptions(cropOptions) }
            if (0 != imageWidth && 0 != imageHeight)
                bu.withMaxResultSize(pickBuilder!!.imageWidth, pickBuilder!!.imageHeight)
        }

        activity?.let { bu.start(activity) } ?: run { bu.start(fragment!!.context!!, fragment) }
    }

    private fun needContainerCrop(): Boolean {
        return null != cropedsList && null != mSelectedUri && cropedsList!!.size != mSelectedUri!!.size
    }
}