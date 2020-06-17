package app.ericn.android_common

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {
    override fun load(
        view: ImageView,
        uri: String
    ) {
        Glide.with(view).load(uri).centerCrop().into(view)
    }
}