package app.ericn.mylibrary

import app.ericn.ericsweather.ui.news.NewsFragment
import app.ericn.mylibrary.network.NewsNetworkModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [NewsNetworkModule::class])
abstract class NewsModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): NewsFragment
}