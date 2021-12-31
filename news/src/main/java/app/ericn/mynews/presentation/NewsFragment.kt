package app.ericn.ericsweather.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.ericn.android_common.ImageLoader
import app.ericn.mynews.GenericViewModelFactory
import app.ericn.mynews.VerticalSpaceItemDecoration
import app.ericn.mynews.databinding.NewsFragmentBinding
import app.ericn.mynews.presentation.NewsAdapter
import app.ericn.mynews.presentation.NewsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    @Inject
    lateinit var viewModelFactory: GenericViewModelFactory<NewsViewModel>

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var binding: NewsFragmentBinding
    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }
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
        adapter.update(data.articles)
    }
}
