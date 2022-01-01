package app.ericn.ericsweather

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainInputModule {
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