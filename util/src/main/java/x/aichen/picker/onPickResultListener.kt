package x.aichen.picker

/**
 * 回调接口
 * 直接回调回去一个选取的路径集合   包括  Select images including JPEG, PNG, GIF and videos including MPEG, MP4
 */
interface onPickResultListener {
    fun onPicked(paths: List<String>)
}