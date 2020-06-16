package app.ericn.ericsweather

import app.ericn.ericsweather.weather.WeatherNetworkModule
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [WeatherNetworkModule::class])
class MainModule {
    @Provides
    @Singleton
    fun provideSearchInputStream(): PublishSubject<String> {
        val subject = PublishSubject.create<String>()
        return subject
    }
}