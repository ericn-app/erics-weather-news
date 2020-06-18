package app.ericn.ericsweather.location

import app.ericn.ericsweather.location.GoogleLocationDataSource
import app.ericn.ericsweather.location.LocationEntity
import io.reactivex.Flowable
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: GoogleLocationDataSource) {
    fun getLocation() : Flowable<LocationEntity> {
        return dataSource.locationObservable.distinctUntilChanged()
    }
}