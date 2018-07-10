package x.aichen.extend

import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import x.aichen.util.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * author 艾晨
 * Created at 2018/7/6 17:07
 * Update at 2018/7/6 17:07
 * Update people:
 * Version:1.0
 * 说明：rxjava的一些扩展
*/


/**
 * 执行io线程  取消注册在io   回掉主线程  并且自动重试两次
 * 扩展方法   支持kotlin
 */

fun <T> Observable<T>.io_main(): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.io_main_retry(): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWhen(RetryWithDelay(2, 20000))
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.retryNum(retryNum: Int): Observable<T> = this.retryWhen(RetryWithDelay(retryNum, 20000))


fun <T> Observable<T>.all_io(): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWhen(RetryWithDelay(2, 20000))
        .observeOn(Schedulers.io())

fun <T, E> Observable<T>.io_main_bindLife(provider: com.trello.rxlifecycle2.LifecycleProvider<E>): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .bindToLifecycle(provider)

fun <T> Observable<T>.io_main_bindLife(view: android.view.View): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .bindToLifecycle(view)

