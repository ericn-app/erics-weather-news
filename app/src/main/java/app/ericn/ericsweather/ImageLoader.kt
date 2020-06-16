package app.ericn.ericsweather

import android.widget.ImageView

interface ImageLoader {

    fun load(
        view: ImageView,
        uri: String
    )
}