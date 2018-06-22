package x.aichen.picker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * author 艾晨
 * Created at 2017/8/7 17:09
 * Update at 2017/8/7 17:09
 * Update people:
 * Version:1.0
 * 说明：    框架里自带的   没有升级到glide4.0   所有自己重写了
 */


public class GlideEngine implements ImageEngine {


    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RequestOptions opt = new RequestOptions();
        opt.placeholder(placeholder)
                .override(resize, resize)
                .centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(opt)
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RequestOptions opt = new RequestOptions();
        opt.placeholder(placeholder)
                .override(resize, resize)
                .centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(opt)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions opt = new RequestOptions();
        opt.override(resizeX, resizeY)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(uri)
                .apply(opt)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
/**
 * Glide.with(context)
 .load(uri)
 .asGif()
 .override(resizeX, resizeY)
 .priority(Priority.HIGH)
 .into(imageView);
 */
        RequestOptions opt = new RequestOptions();
        opt.override(resizeX, resizeY)
                .priority(Priority.HIGH);
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(opt)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
