package leonov.ru.translator.utils.sound

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import leonov.ru.translator.utils.addHttpsPrefix
import java.io.IOException


class SoundHelper(private val context: Context) {

    fun playUrl(url: String) {
        val mp = MediaPlayer()
        try {
            mp.setDataSource(context, Uri.parse(url))
            mp.prepare()
            mp.start()
        } catch (e: IOException) {
            Log.e("SOUND", "prepare() failed")
        }
    }
}