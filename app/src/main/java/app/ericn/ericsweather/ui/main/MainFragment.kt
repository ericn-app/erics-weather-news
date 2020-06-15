package app.ericn.ericsweather.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.R
import app.ericn.ericsweather.RetrofitClient
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import app.ericn.ericsweather.ui.main.core.WeatherRepository
import app.ericn.ericsweather.ui.main.network.WeatherApi

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val api = RetrofitClient.retrofit().create(WeatherApi::class.java)
        val repository = WeatherRepository(api)
        val interactor = CurrentWeatherInteractor(repository)
        val vmFactory = MainViewModelFactory(interactor)
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(MainViewModel::class.java)
    }

}