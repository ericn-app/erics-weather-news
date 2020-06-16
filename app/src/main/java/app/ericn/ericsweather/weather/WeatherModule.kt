package app.ericn.ericsweather.weather

import app.ericn.ericsweather.MainActivity
import app.ericn.ericsweather.MainModule
import app.ericn.ericsweather.weather.presentation.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WeatherModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherFragment
}