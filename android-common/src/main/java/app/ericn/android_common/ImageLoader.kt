package app.ericn.android_common

import android.widget.ImageView

interface ImageLoader {

    fun load(
        view: ImageView,
        uri: String
    )
}