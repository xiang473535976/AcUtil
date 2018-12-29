package x.aichen.extend

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.kotlin.bindToLifecycle
import com.trello.rxlifecycle3.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import x.aichen.base.XBaseActivity
import java.util.concurrent.TimeUnit


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

/**
 * 自动重试两次 10秒间隔
 */
fun <T> Observable<T>.io_main_retry(): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWithDelay(2, 10000)
        .observeOn(AndroidSchedulers.mainThread())

/**
 *重试次数
 */
fun <T> Observable<T>.retryNum(retryNum: Int): Observable<T> = this.retryWithDelay(retryNum, 10000)


fun <T> Observable<T>.all_io(): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .retryWithDelay(2, 10000)
        .observeOn(Schedulers.io())

/**
 * 绑定生命周期
 */
fun <T, E> Observable<T>.io_main_bindLife(provider: com.trello.rxlifecycle3.LifecycleProvider<E>): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .bindToLifecycle(provider)

/**
 * 指定任意的生命周期   绑定    参考ActivityEvent里面的枚举
 */
fun <T> Observable<T>.io_main_bindLifeByEvent(activityEvent: ActivityEvent): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(ActivityUtils.getTopActivity() as XBaseActivity, activityEvent)

fun <T> Observable<T>.io_main_bindLife(view: android.view.View): Observable<T> = this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .bindToLifecycle(view)

/**
 *maxRetries   重试次数
 *retryDelayMillis   每次重试时间
 */
private var retryCount: Int = 0

fun <T> Observable<T>.retryWithDelay(maxRetries: Int, retryDelayMillis: Long): Observable<T> =
        retryWhen {
            it.flatMap { throwable ->
                if (++retryCount <= maxRetries) {
                    LogUtils.e("get error, it will try after " + retryDelayMillis
                            + " millisecond, retry count " + retryCount)
                    Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
                } else {
                    Observable.error(throwable)
                }
            }

        }


