package app.ericn.ericsweather

import app.ericn.android_common.MiscModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [MiscModule::class])
@InstallIn(SingletonComponent::class)
class AppModule