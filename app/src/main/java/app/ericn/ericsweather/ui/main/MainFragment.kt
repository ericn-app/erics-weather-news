package app.ericn.ericsweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.RetrofitClient
import app.ericn.ericsweather.StringProviderImpl
import app.ericn.ericsweather.databinding.MainFragmentBinding
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import app.ericn.ericsweather.ui.main.core.GlideImageLoader
import app.ericn.ericsweather.ui.main.core.WeatherRepository
import app.ericn.ericsweather.ui.main.network.CurrentWeatherResponse
import app.ericn.ericsweather.ui.main.network.WeatherApi
import app.ericn.ericsweather.ui.main.presentation.CurrentWeatherUI
import app.ericn.ericsweather.ui.main.presentation.MainViewModel
import app.ericn.ericsweather.ui.main.presentation.MainViewModelFactory

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val imageLoader = GlideImageLoader()
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val api = RetrofitClient.retrofit().create(WeatherApi::class.java)
        val repository = WeatherRepository(api)
        val interactor = CurrentWeatherInteractor(repository)
        val vmFactory =
            MainViewModelFactory(
                interactor,
                StringProviderImpl(resources)
            )
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(MainViewModel::class.java)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is MainViewModel.ViewState.DataLoaded -> renderData(state.currentWeather)
                MainViewModel.ViewState.Loading -> {
                    // show loading animation
                }
                is MainViewModel.ViewState.Error -> renderError(state.message)
            }
        })
    }

    private fun renderError(message: String) {

    }

    private fun renderData(currentWeather: CurrentWeatherUI) {
        binding.cityName.text = currentWeather.cityName
        binding.maxMin.text = currentWeather.maxMin
        binding.current.text = currentWeather.currentTemp
        imageLoader.load(binding.iconToday, currentWeather.symbol)
    }

}