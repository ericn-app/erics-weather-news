package app.ericn.ericsweather.location

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPermissionsHelper @Inject constructor(private val context: Application) {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 0x0001
    }

    fun isLocationPermissionGranted(): Boolean {
        return isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun requestLocationPermission(fragment: Fragment) {
        requestPermission(fragment, Manifest.permission.ACCESS_COARSE_LOCATION,
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    fun requestLocationPermission(activity: Activity) {
        requestPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION,
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    private fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
        if (isPermissionGranted(permission).not()) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

}