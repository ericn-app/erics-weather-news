package app.ericn.ericsweather

import app.ericn.ericsweather.weather.WeatherModule
import app.ericn.mylibrary.NewsModule
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [WeatherModule::class, NewsModule::class])
class MainModule {
    @Provides
    @Singleton
    fun provideSearchInputStream(): PublishSubject<String> {
        return PublishSubject.create()
    }

    @Provides
    @Singleton
    fun provideSearchInputStreamReadyOnly(subject: PublishSubject<String>): Observable<String> {
        return subject.debounce(500L, TimeUnit.MILLISECONDS)
    }
}