package app.ericn.ericsweather

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module(includes = [app.ericn.android_common.MiscModule::class])
abstract class AppModule {
    @Binds
    abstract fun application(app: TheApplication): Application

    @ContributesAndroidInjector(modules = [MainModule::class])
    @Singleton
    abstract fun contributeMainActivity(): MainActivity
}