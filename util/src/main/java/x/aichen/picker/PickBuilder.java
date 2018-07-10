package x.aichen.picker;

import android.content.pm.ActivityInfo;

import com.blankj.utilcode.util.AppUtils;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.SelectionSpec;

import java.util.List;
import java.util.Set;

/**
 * author 艾晨
 * Created at 2017/8/4 22:06
 * Update at 2017/8/4 22:06
 * Update people:
 * Version:1.0
 * 说明：  图片选择器的配置
 */


public class PickBuilder {
    /**
     * 图片选择
     */
    private SelectionSpec mSelectionSpec;
    private Set<MimeType> mMimeType = MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP);//  默认选择图片   去除gif
    private int mThemeId = 1;   //com.zhihu.matisse.R.style.Matisse_Zhihu
    private int mOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    private boolean mCountable = true;  //可数的
    private int mMaxSelectable = 9;//  最大选择张数   默认9
    private List<Filter> mFilters;  //过滤操作  参考https://github.com/zhihu/Matisse/blob/master/sample/src/main/java/com/zhihu/matisse/sample/GifSizeFilter.java
    private boolean mCapture = true; //  是否显示拍照
    private CaptureStrategy mCaptureStrategy = new CaptureStrategy(true, AppUtils.getAppPackageName() + ".fileprovider");  //拍照配置   //eg   aichen.green.fileprovider
    private int mSpanCount = 3;//  选图的时候     gridview数量
    private int mGridExpectedSize;  //
    private float mThumbnailScale = 0.85f;  //缩略图
    private ImageEngine mImageEngine = new GlideEngine();//  图片下载器  默认glide
    /**
     * 图片剪切
     */
    private int imageWidth;
    private int imageHeight;
    private boolean crop;

    public UCrop.Options getCropOptions() {
        return cropOptions;
    }

    /**
     * @param cropOptions 剪切图片配置
     * @return
     */
    public PickBuilder setCropOptions(UCrop.Options cropOptions) {
        this.cropOptions = cropOptions;
        return this;
    }

    private UCrop.Options cropOptions;  //剪切的一些其他配置


    /**
     * 图片压缩  配置
     */
    private boolean compress;  //是否开始压缩


    public SelectionSpec getmSelectionSpec() {
        return mSelectionSpec;
    }

    public PickBuilder setmSelectionSpec(SelectionSpec mSelectionSpec) {
        this.mSelectionSpec = mSelectionSpec;
        return this;
    }

    public Set<MimeType> getmMimeType() {
        return mMimeType;
    }

    public PickBuilder setmMimeType(Set<MimeType> mMimeType) {
        this.mMimeType = mMimeType;
        return this;
    }

    public int getmThemeId() {
        return mThemeId;
    }

    public PickBuilder setmThemeId(int mThemeId) {
        this.mThemeId = mThemeId;
        return this;
    }

    public int getmOrientation() {
        return mOrientation;
    }

    public PickBuilder setmOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
        return this;
    }

    public boolean ismCountable() {
        return mCountable;
    }

    public PickBuilder setmCountable(boolean mCountable) {
        this.mCountable = mCountable;
        return this;
    }

    public int getmMaxSelectable() {
        return mMaxSelectable;
    }

    public PickBuilder setmMaxSelectable(int mMaxSelectable) {
        this.mMaxSelectable = mMaxSelectable;
        return this;
    }

    public List<Filter> getmFilters() {
        return mFilters;
    }

    public PickBuilder setmFilters(List<Filter> mFilters) {
        this.mFilters = mFilters;
        return this;
    }

    public boolean ismCapture() {
        return mCapture;
    }

    public PickBuilder setmCapture(boolean mCapture) {
        this.mCapture = mCapture;
        return this;
    }

    public CaptureStrategy getmCaptureStrategy() {
        return mCaptureStrategy;
    }

    public PickBuilder setmCaptureStrategy(CaptureStrategy mCaptureStrategy) {
        this.mCaptureStrategy = mCaptureStrategy;
        return this;
    }

    public int getmSpanCount() {
        return mSpanCount;
    }

    public PickBuilder setmSpanCount(int mSpanCount) {
        this.mSpanCount = mSpanCount;
        return this;
    }

    public int getmGridExpectedSize() {
        return mGridExpectedSize;
    }

    public PickBuilder setmGridExpectedSize(int mGridExpectedSize) {
        this.mGridExpectedSize = mGridExpectedSize;
        return this;
    }

    public float getmThumbnailScale() {
        return mThumbnailScale;
    }

    public PickBuilder setmThumbnailScale(float mThumbnailScale) {
        this.mThumbnailScale = mThumbnailScale;
        return this;
    }

    public ImageEngine getmImageEngine() {
        return mImageEngine;
    }

    public PickBuilder setmImageEngine(ImageEngine mImageEngine) {
        this.mImageEngine = mImageEngine;
        return this;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public PickBuilder setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public PickBuilder setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public boolean isCrop() {
        return crop;
    }

    public PickBuilder setCrop(boolean crop) {
        this.crop = crop;
        return this;
    }

    public boolean isCompress() {
        return compress;
    }

    public PickBuilder setCompress(boolean compress) {
        this.compress = compress;
        return this;
    }
}
