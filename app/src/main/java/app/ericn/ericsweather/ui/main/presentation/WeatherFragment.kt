package app.ericn.ericsweather.ui.main.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.RetrofitClient
import app.ericn.ericsweather.StringProviderImpl
import app.ericn.ericsweather.databinding.MainFragmentBinding
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import app.ericn.ericsweather.ui.main.core.GlideImageLoader
import app.ericn.ericsweather.ui.main.core.WeatherForecastInteractor
import app.ericn.ericsweather.ui.main.core.WeatherRepository
import app.ericn.ericsweather.ui.main.network.WeatherApi
import io.reactivex.subjects.PublishSubject

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            WeatherFragment()
    }

    private val imageLoader = GlideImageLoader()
    private val searchSubject = PublishSubject.create<String>()
    private lateinit var binding: MainFragmentBinding
    private val searchQueryListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            println("onQueryTextSubmit $query")
            searchSubject.onNext(query)
            binding.search.clearFocus()
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            println("onQueryTextChange $newText")
            return true
        }
    }
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitClient.retrofit().create(WeatherApi::class.java)
        val repository = WeatherRepository(api)
        val currentInteractor = CurrentWeatherInteractor(repository)
        val forecastInteractor = WeatherForecastInteractor(repository)
        val vmFactory =
            MainViewModelFactory(
                currentInteractor,
                forecastInteractor,
                StringProviderImpl(resources),
                searchSubject
            )
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(WeatherViewModel::class.java)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is WeatherViewModel.ViewState.DataLoaded -> renderData(state)
                WeatherViewModel.ViewState.Loading -> {
                    // show loading animation
                }
                is WeatherViewModel.ViewState.Error -> renderError(state.message)
            }
        })

        binding.search.setOnQueryTextListener(searchQueryListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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