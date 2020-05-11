package leonov.ru.translator.view.sound

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.IOException


class SoundHelper(private val context: Context) {

    private val httpsPrefix = "https:"

    fun playUrl(url: String) {
        val mp = MediaPlayer()
        try {
            mp.setDataSource(context, Uri.parse("$httpsPrefix$url"))
            mp.prepare()
            mp.start()
        } catch (e: IOException) {
            Log.e("SOUND", "prepare() failed")
        }
    }
}