package leonov.ru.translator.utils.image

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader() {

    fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}