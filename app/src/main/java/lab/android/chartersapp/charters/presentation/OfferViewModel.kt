package lab.android.chartersapp.charters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import lab.android.chartersapp.charters.data.OfferApiService
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.Offer

@HiltViewModel
class OfferViewModel @Inject constructor(private val apiService: OfferApiService) : ViewModel() {
    private val _offers = mutableStateOf<ApiState<List<Offer>>>(ApiState.Loading)
    val offers: State<ApiState<List<Offer>>> = _offers

    fun fetchOffers() {
        viewModelScope.launch {
            _offers.value = ApiState.Loading // Set loading state initially
            try {
                val response = apiService.getOffers()
                if (response.isSuccessful && response.body() != null) {
                    _offers.value = ApiState.Success(response.body()!!)
                } else {
                    _offers.value = ApiState.Error("Error fetching offers: ${response.message()}")
                }
            } catch (e: Exception) {
                _offers.value = ApiState.Error("Failed to fetch data: ${e.message}")
            }
        }
    }
}
