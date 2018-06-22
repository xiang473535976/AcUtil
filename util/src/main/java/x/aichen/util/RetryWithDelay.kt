package x.aichen.util

import com.blankj.utilcode.util.LogUtils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Contract->Model->Presenter->Activity->Module->Component
 * author 艾晨
 * Created at 2017/5/21 10:10
 * Update at 2017/5/21 10:10
 * Update people:
 * Version:1.0
 * 说明：  //总共重试3次，重试间隔3000毫秒  .retryWhen(new RetryWithDelay(3, 3000))
 */


class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Long) : Function<Observable<out Throwable>, Observable<*>> {
    private var retryCount: Int = 0

    override fun apply(attempts: Observable<out Throwable>): Observable<*> {
        return attempts
//                .flatMap<*>(Function<Throwable, Observable<*>> { throwable ->
//                    if (++retryCount <= maxRetries) {
//                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
//                        LogUtils.e("", "get error, it will try after " + retryDelayMillis
//                                + " millisecond, retry count " + retryCount)
//                        return@Function Observable.timer(retryDelayMillis.toLong(),
//                                TimeUnit.MILLISECONDS)
//                    }
//                    // Max retries hit. Just pass the error along.
//                    Observable.error<*>(throwable)
//                })
                .flatMap { throwable ->
                    if (++retryCount <= maxRetries) {
                        LogUtils.e("get error, it will try after " + retryDelayMillis
                                + " millisecond, retry count " + retryCount)
                        Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
                    } else {
                        Observable.error(throwable)
                    }
                }
    }

}