package app.ericn.ericsweather.weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.weather.RetrofitClient
import app.ericn.ericsweather.StringProviderImpl
import app.ericn.ericsweather.databinding.MainFragmentBinding
import app.ericn.ericsweather.weather.core.CurrentWeatherInteractor
import app.ericn.ericsweather.GlideImageLoader
import app.ericn.ericsweather.weather.core.WeatherForecastInteractor
import app.ericn.ericsweather.weather.core.WeatherRepository
import app.ericn.ericsweather.weather.network.WeatherApi
import dagger.android.support.DaggerFragment
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    private lateinit var binding: MainFragmentBinding
    private val imageLoader = GlideImageLoader()
    private val viewModel: WeatherViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val api = RetrofitClient.retrofit().create(WeatherApi::class.java)
//        val repository = WeatherRepository(api)
//        val currentInteractor = CurrentWeatherInteractor(repository)
//        val forecastInteractor = WeatherForecastInteractor(repository)
//
//        println(viewModelFactory)
//        val vmFactory =
//            WeatherViewModelFactory(
//                currentInteractor,
//                forecastInteractor,
//                StringProviderImpl(resources),
//                searchSubject
//            )
//        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(WeatherViewModel::class.java)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is WeatherViewModel.ViewState.DataLoaded -> renderData(state)
                WeatherViewModel.ViewState.Loading -> {
                    // show loading animation
                }
                is WeatherViewModel.ViewState.Error -> renderError(state.message)
            }
        })
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