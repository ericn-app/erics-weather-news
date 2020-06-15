package app.ericn.ericsweather

import android.content.res.Resources
import androidx.annotation.StringRes

class StringProviderImpl(private val resources: Resources) : StringProvider {
    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        resources.getString(resId, *formatArgs)
}