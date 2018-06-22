package x.aichen.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import top.wefor.circularanim.CircularAnim
import java.io.Serializable

/**
 * 波纹形打开页面

 * @param cls
 * *
 * @param view
 */
inline fun <reified T : Activity> Context.circulTo(view: View, vararg params: Pair<String, Any?>) {
    CircularAnim.fullActivity(ActivityUtils.getTopActivity(), view)
            .go {
                startActivity(createIntent(applicationContext, T::class.java, params))
            }
}

/**
 * 普通跳转
 */
inline fun <reified T : Activity> Context.simpleTo(vararg params: Pair<String, Any?>) {
    startActivity(createIntent(applicationContext, T::class.java, params))
}

fun <T> createIntent(ctx: Context, clazz: Class<out T>, params: Array<out Pair<String, Any?>>): Intent {
    val intent = Intent(ctx, clazz)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    return intent
}

inline fun <reified T : Activity> Context.simpleToWithAnim(transitView: View, vararg params: Pair<String, Any?>) {
    val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(transitView, transitView.x.toInt(), transitView.y.toInt(), 0, 0)
    val intent = createIntent(applicationContext, T::class.java, params)
    try {
        ActivityCompat.startActivity(ActivityUtils.getTopActivity(), intent, optionsCompat.toBundle())
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        ActivityUtils.getTopActivity().startActivity(intent)
    }

}

private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    with(intent) {
        params.forEach {
            val value = it.second
            when (value) {
                null -> putExtra(it.first, null as Serializable?)
                is Int -> putExtra(it.first, value)
                is Long -> putExtra(it.first, value)
                is CharSequence -> putExtra(it.first, value)
                is String -> putExtra(it.first, value)
                is Float -> putExtra(it.first, value)
                is Double -> putExtra(it.first, value)
                is Char -> putExtra(it.first, value)
                is Short -> putExtra(it.first, value)
                is Boolean -> putExtra(it.first, value)
                is Serializable -> putExtra(it.first, value)
                is Bundle -> putExtra(it.first, value)
                is Parcelable -> putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                    value.isArrayOf<String>() -> putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                    else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is IntArray -> putExtra(it.first, value)
                is LongArray -> putExtra(it.first, value)
                is FloatArray -> putExtra(it.first, value)
                is DoubleArray -> putExtra(it.first, value)
                is CharArray -> putExtra(it.first, value)
                is ShortArray -> putExtra(it.first, value)
                is BooleanArray -> putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            return@forEach
        }
    }

}

/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_ANIMATION] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * Add the [Intent.FLAG_ACTIVITY_SINGLE_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }
