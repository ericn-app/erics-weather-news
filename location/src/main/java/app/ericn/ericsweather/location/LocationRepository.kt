package app.ericn.ericsweather.location

import io.reactivex.Flowable
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: GoogleLocationDataSource) {
    fun getLocation(): Flowable<LocationEntity> {
        return dataSource.locationObservable.distinctUntilChanged()
    }
}