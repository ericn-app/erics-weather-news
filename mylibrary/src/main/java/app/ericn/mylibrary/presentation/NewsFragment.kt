package app.ericn.ericsweather.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.ericn.mylibrary.R
import app.ericn.mylibrary.presentation.NewsViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            showLoading(false)
            when (state) {
                is NewsViewModel.ViewState.DataLoaded -> TODO()
                NewsViewModel.ViewState.Loading -> showLoading(true)
                is NewsViewModel.ViewState.Error -> TODO()
            }
        })
    }

    private fun showLoading(b: Boolean) {

    }

    private fun renderError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(data: NewsViewModel.ViewState.DataLoaded) {
        println("renderData")
    }
}
