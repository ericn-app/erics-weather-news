package app.ericn.ericsweather

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<TheApplication> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<TheApplication>
}