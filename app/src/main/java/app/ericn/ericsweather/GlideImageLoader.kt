package app.ericn.ericsweather

import android.widget.ImageView
import app.ericn.ericsweather.ImageLoader
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {
    override fun load(
        view: ImageView,
        uri: String
    ) {
        Glide.with(view).load(uri).into(view)
    }
}