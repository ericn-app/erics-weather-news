package app.ericn.ericsweather

import app.ericn.ericsweather.weather.WeatherModule
import app.ericn.mynews.NewsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainWrapperModule {
    @ContributesAndroidInjector(modules = [WeatherModule::class, NewsModule::class])
    abstract fun contributeMainActivity(): MainActivity
}