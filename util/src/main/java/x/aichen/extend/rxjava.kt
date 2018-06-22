package x.aichen.extend

import x.aichen.util.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Contract->Model->Presenter->Activity->Module->Component
 * author 艾晨
 * Created at 2017/6/10 10:04
 * Update at 2017/6/10 10:04
 * Update people:
 * Version:1.0
 * 说明：rxjava的一些扩展
 */

/**
 * 执行io线程  取消注册在io   回掉主线程  并且自动重试两次
 * 扩展方法   支持kotlin
 */

fun <T> Observable<T>.io_main(): Observable<T> = this.
        subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.io_main_retry(): Observable<T> = this.
        subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWhen(RetryWithDelay(2, 20000))
        .observeOn(AndroidSchedulers.mainThread())

/**
 * 扩展方法   支持kotlin
 */

fun <T> Observable<T>.retryNum(retryNum: Int): Observable<T> = this.
        retryWhen(RetryWithDelay(retryNum, 20000))


/**
 * 扩展方法   支持kotlin
 */

fun <T> Observable<T>.all_io(): Observable<T> = this.
        subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWhen(RetryWithDelay(2, 20000))
        .observeOn(Schedulers.io())

