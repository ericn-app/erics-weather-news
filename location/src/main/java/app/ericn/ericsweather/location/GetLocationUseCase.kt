package app.ericn.ericsweather.location

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    operator fun invoke(): Flowable<LocationEntity> {
        return repository.getLocation()
    }
}