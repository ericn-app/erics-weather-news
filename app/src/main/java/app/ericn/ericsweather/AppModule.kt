package app.ericn.ericsweather

import android.app.Application
import app.ericn.ericsweather.weather.WeatherModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module(includes = [MiscModule::class])
abstract class AppModule {
    @Binds
    abstract fun application(app: TheApplication): Application

    @ContributesAndroidInjector(modules = [MainModule::class, WeatherModule::class])
    @Singleton
    abstract fun contributeMainActivity(): MainActivity
}