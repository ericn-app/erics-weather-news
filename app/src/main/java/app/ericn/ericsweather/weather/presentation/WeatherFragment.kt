package app.ericn.ericsweather.weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import app.ericn.android_common.ImageLoader
import app.ericn.ericsweather.databinding.WeatherFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var binding: WeatherFragmentBinding
    private val viewModel: WeatherViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            showLoading(false)
            when (state) {
                is WeatherViewModel.ViewState.DataLoaded -> renderData(state)
                WeatherViewModel.ViewState.Loading -> showLoading(true)
                is WeatherViewModel.ViewState.Error -> renderError(state.message)
            }
        })
    }

    private fun showLoading(b: Boolean) {

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