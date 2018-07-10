package x.aichen.extend

import java.lang.Double
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * 格式化金额

 * @param s
 * *
 * @param len 保留几位小数
 * *
 * @return
 */
fun String.formatDecimalNumBer(s: String?, len: Int): String {
    if (s == null || s.isEmpty()) {
        return ""
    }
    var zero = StringBuffer()
    var formater: NumberFormat?
    val num = Double.parseDouble(s)
    formater = if (len == 0) {
//            formater = DecimalFormat("###,###")
        DecimalFormat("######")

    } else {
        val buff = StringBuffer()

        buff.append("######.")
        for (i in 0 until len) {
            buff.append("#")
            zero.append("0")
        }
        DecimalFormat(buff.toString())
    }
    var result = formater.format(num)
    if (result.indexOf(".") == -1)
        result = "" + result + "." + zero.toString()
    return result.toString()
}
