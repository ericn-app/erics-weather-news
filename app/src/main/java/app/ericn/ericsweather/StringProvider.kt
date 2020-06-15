package app.ericn.ericsweather

import android.content.res.Resources
import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes resId: Int, vararg param : Any) : String
}