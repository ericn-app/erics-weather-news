package app.ericn.mynews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class GenericViewModelFactory<T1 : ViewModel> @Inject constructor(
    private val provider: Provider<T1>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}