package app.ericn.ericsweather

import android.app.Application
import com.bugsnag.android.Bugsnag
import com.rollbar.android.Rollbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TheApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this).inject(this)

        Bugsnag.start(this)

        Rollbar.init(this);
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }
}