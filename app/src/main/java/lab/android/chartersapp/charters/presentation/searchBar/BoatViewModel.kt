package lab.android.chartersapp.charters.presentation.searchBar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.data.repositories.BoatRepository
import javax.inject.Inject

@HiltViewModel
class BoatViewModel @Inject constructor(
    private val boatRepository: BoatRepository
) : ViewModel() {

    private val _boats = MutableLiveData<ApiState<List<Boat>>>(ApiState.Loading)
    val boats: LiveData<ApiState<List<Boat>>> = _boats

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredBoats = MutableStateFlow<List<Boat>>(emptyList())
    val filteredBoats: StateFlow<List<Boat>> = _filteredBoats.asStateFlow()

    fun getBoats() {
        viewModelScope.launch {
            try {
                val result = boatRepository.fetchBoats()
                Log.d("API Response", "Boats fetched successfully: $result") // Log response
                _boats.value = ApiState.Success(result)
                _filteredBoats.value = result // Populate filtered list initially
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch boats: ${e.message}") // Log error
                _boats.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.update { query }
        filterBoats()
    }

    private fun filterBoats() {
        val currentBoats = (_boats.value as? ApiState.Success)?.data.orEmpty()
        _filteredBoats.value = if (searchQuery.value.isBlank()) {
            currentBoats
        } else {
            currentBoats.filter { boat ->
                boat.name.contains(searchQuery.value, ignoreCase = true) ||
                        boat.boatModel.contains(searchQuery.value, ignoreCase = true)
            }
        }
    }

    fun getBoatByName(name: String?): Boat? {
                // Extract boats from _boats LiveData if the state is ApiState.Success
        val currentBoats = (boats.value as? ApiState.Success)?.data.orEmpty()
        Log.i("boats",currentBoats.toString())

        // Find the boat by name in the extracted list
        return currentBoats.find { boat -> boat.name.equals(name, ignoreCase = true) }
    }

}
