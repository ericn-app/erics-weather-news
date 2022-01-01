package app.ericn.ericsweather.weather.network

import app.ericn.ericsweather.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class WeatherNetworkModule {
    @Provides
    @Named("weather retrofit")
    fun provideWeatherRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("lang", "en-ca")
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("appid", "9789793117ca146639dab6932eb409fb")
                    .build()

                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideWeatherApi(@Named("weather retrofit") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}