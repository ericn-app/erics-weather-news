package app.ericn.android_common

import android.content.res.Resources
import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes resId: Int, vararg param : Any) : String
}