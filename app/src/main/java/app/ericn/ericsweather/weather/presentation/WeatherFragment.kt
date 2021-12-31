package app.ericn.ericsweather.weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.ericn.android_common.ImageLoader
import app.ericn.ericsweather.databinding.WeatherFragmentBinding
import app.ericn.ericsweather.location.LocationPermissionsHelper
import app.ericn.mynews.GenericViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    @Inject
    lateinit var viewModelFactory: GenericViewModelFactory<WeatherViewModel>

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var permissionsHelper: LocationPermissionsHelper

    private lateinit var binding: WeatherFragmentBinding
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showEmptyState(true)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            showLoading(false)
            showEmptyState(false)
            when (state) {
                is WeatherViewModel.ViewState.DataLoaded -> renderData(state)
                WeatherViewModel.ViewState.Loading -> showLoading(true)
                is WeatherViewModel.ViewState.Error -> renderError(state.message)
            }
        })

        if (savedInstanceState == null) {
            handleLocationPermission()
        }
    }

    private fun handleLocationPermission() {
        if (permissionsHelper.isLocationPermissionGranted()) {
            viewModel.onLocationPermissionGranted()
        } else {
            permissionsHelper.requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionResult(requestCode, grantResults)
    }

    private fun showEmptyState(b: Boolean) {
        binding.emptyState.visibility = if (b) View.VISIBLE else View.GONE
        binding.todayHeader.visibility = if (b) View.GONE else View.VISIBLE
        binding.cityName.visibility = if (b) View.GONE else View.VISIBLE
        binding.maxMin.visibility = if (b) View.GONE else View.VISIBLE
        binding.current.visibility = if (b) View.GONE else View.VISIBLE
        binding.nextDays.visibility = if (b) View.GONE else View.VISIBLE
    }

    private fun showLoading(b: Boolean) {
        binding.loader.visibility = if (b) View.VISIBLE else View.GONE
    }

    private fun renderError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(data: WeatherViewModel.ViewState.DataLoaded) {
        binding.cityName.text = data.currentWeather.cityName
        binding.maxMin.text = data.currentWeather.maxMin
        binding.current.text = data.currentWeather.currentTemp
        imageLoader.load(binding.iconToday, data.currentWeather.symbol)
        imageLoader.load(binding.symbolTomorrow, data.tomorrow.symbol)
        imageLoader.load(binding.symbolDayAfter, data.dayAfter.symbol)
        imageLoader.load(binding.symbolTwoDaysLater, data.twoDaysLater.symbol)
        binding.tempTomorrow.text = data.tomorrow.maxMin
        binding.tempDayAfter.text = data.dayAfter.maxMin
        binding.tempTwoDaysLater.text = data.twoDaysLater.maxMin
    }

}