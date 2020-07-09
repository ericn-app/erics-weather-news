package app.ericn.ericsweather

import android.app.Application
import app.ericn.android_common.MiscModule
import dagger.Binds
import dagger.Module


@Module(includes = [MiscModule::class, MainWrapperModule::class, MainInputModule::class])
abstract class AppModule {
    @Binds
    abstract fun application(app: TheApplication): Application
}