package leonov.ru.translator.utils.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadByUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(
            RequestOptions()
            .transform(
                CenterCrop(),
                RoundedCorners(10)
            )
        )
        .into(this)
}
