package x.aichen.extend

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

/**通过文件的uri   获取文件的真实路径
 *
 */
fun Uri.toFileRealPath(context: Context): String? {
    var data: String? = null
    if (scheme == null)
        data = path
    else if (ContentResolver.SCHEME_FILE == scheme) {
        data = path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(this, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                }
            }
            cursor.close()
        }
    }
    return data
}