package app.ericn.ericsweather.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import app.ericn.android_common.ImageLoader
import app.ericn.mylibrary.VerticalSpaceItemDecoration
import app.ericn.mylibrary.databinding.NewsFragmentBinding
import app.ericn.mylibrary.presentation.NewsAdapter
import app.ericn.mylibrary.presentation.NewsViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var binding: NewsFragmentBinding
    private val viewModel: NewsViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showEmptyState(true)

        viewModel.viewStateReadOnly.observe(viewLifecycleOwner, Observer { state ->
            showLoading(false)
            showEmptyState(false)
            when (state) {
                is NewsViewModel.ViewState.DataLoaded -> renderData(state)
                NewsViewModel.ViewState.Loading -> showLoading(true)
                is NewsViewModel.ViewState.Error -> renderError(state.message)
            }
        })

        adapter = NewsAdapter(imageLoader)
        binding.articles.addItemDecoration(VerticalSpaceItemDecoration(48))
        binding.articles.adapter = adapter
    }

    private fun showEmptyState(b: Boolean) {
        binding.emptyState.visibility = if (b) View.VISIBLE else View.GONE
        binding.newsHeader.visibility = if (b) View.GONE else View.VISIBLE
        binding.articles.visibility = if (b) View.GONE else View.VISIBLE
    }

    private fun showLoading(b: Boolean) {

    }

    private fun renderError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(data: NewsViewModel.ViewState.DataLoaded) {
        println("renderData")
        adapter.update(data.articles)
    }
}
