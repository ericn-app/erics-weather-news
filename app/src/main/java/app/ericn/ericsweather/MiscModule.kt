package app.ericn.ericsweather

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class MiscModule {
    @Provides
    fun provideStringProvider(context: Application): StringProvider {
        return StringProviderImpl(context.resources)
    }
}