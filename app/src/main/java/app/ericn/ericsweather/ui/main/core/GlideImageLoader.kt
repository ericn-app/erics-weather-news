package app.ericn.ericsweather.ui.main.core

import android.widget.ImageView
import app.ericn.ericsweather.ui.main.ImageLoader
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {
    override fun load(
        view: ImageView,
        uri: String
    ) {
        Glide.with(view).load(uri).into(view)
    }
}