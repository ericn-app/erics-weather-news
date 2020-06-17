package app.ericn.mynews

import app.ericn.ericsweather.ui.news.NewsFragment
import app.ericn.mynews.network.NewsNetworkModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [NewsNetworkModule::class])
abstract class NewsModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): NewsFragment
}