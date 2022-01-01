package app.ericn.ericsweather

import android.app.Application
import com.bugsnag.android.Bugsnag
import com.rollbar.android.Rollbar
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Bugsnag.start(this)

        Rollbar.init(this);
    }
}