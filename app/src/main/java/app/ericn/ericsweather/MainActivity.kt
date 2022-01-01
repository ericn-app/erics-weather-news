package app.ericn.ericsweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import app.ericn.ericsweather.databinding.MainActivityBinding
import app.ericn.ericsweather.ui.news.NewsFragment
import app.ericn.ericsweather.weather.presentation.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var searchSubject: PublishSubject<String>

    private lateinit var binding: MainActivityBinding
    private val searchQueryListener = object : SearchView.OnQueryTextListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.setOnQueryTextListener(searchQueryListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containers_weather, WeatherFragment.newInstance())
                .commitNow()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_news, NewsFragment.newInstance()).commitNow()
        }
    }
}