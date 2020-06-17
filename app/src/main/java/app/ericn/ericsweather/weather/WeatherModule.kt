package app.ericn.ericsweather.weather

import app.ericn.ericsweather.weather.network.WeatherNetworkModule
import app.ericn.ericsweather.weather.presentation.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [WeatherNetworkModule::class])
abstract class WeatherModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherFragment
}