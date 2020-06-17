package app.ericn.android_common

import android.content.res.Resources
import androidx.annotation.StringRes

class StringProviderImpl(private val resources: Resources) :
    app.ericn.android_common.StringProvider {
    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        resources.getString(resId, *formatArgs)
}