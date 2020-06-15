package app.ericn.ericsweather.ui.main

import android.widget.ImageView

interface ImageLoader {

    fun load(
        view: ImageView,
        uri: String
    )
}