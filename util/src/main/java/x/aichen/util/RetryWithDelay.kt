package x.aichen.util

import com.blankj.utilcode.util.LogUtils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
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