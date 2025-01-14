package lab.android.chartersapp.charters.presentation.searchBar

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.data.dataclasses.BoatPhoto
import lab.android.chartersapp.charters.data.dataclasses.Charter
import lab.android.chartersapp.charters.data.repositories.BoatRepository
import lab.android.chartersapp.charters.data.repositories.CharterRepository
import javax.inject.Inject

@HiltViewModel
class BoatViewModel @Inject constructor(
    private val boatRepository: BoatRepository,
    private val charterRepository: CharterRepository
) : ViewModel() {

    private val _boats = MutableLiveData<ApiState<List<Boat>>>(ApiState.Loading)
    val boats: LiveData<ApiState<List<Boat>>> = _boats

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredBoats = MutableStateFlow<List<Boat>>(emptyList())
    val filteredBoats: StateFlow<List<Boat>> = _filteredBoats.asStateFlow()

    private val _boatPhotos = MutableLiveData<ApiState<List<String>>>(ApiState.Loading)
    val boatPhotos: LiveData<ApiState<List<String>>> = _boatPhotos

    private val _boatImages = MutableLiveData<ApiState<List<Bitmap>>>(ApiState.Loading)
    val boatImages: LiveData<ApiState<List<Bitmap>>> = _boatImages

    private val _charters = MutableLiveData<ApiState<List<Charter>>>(ApiState.Loading)
    val charters: LiveData<ApiState<List<Charter>>> = _charters


    fun getCharters(boatName: String) {
        viewModelScope.launch {
            try {
                val result = charterRepository.fetchChartersByBoat(boatName)
                Log.d("API Response", "Charters fetched successfully: $result")
                _charters.value = ApiState.Success(result)
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch charters: ${e.message}")
                _charters.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

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

    fun getBoatPhotos(boatName: String) {
        viewModelScope.launch {
            var retryCount = 0
            val maxRetries = 3
            val retryDelay = 1000L // 1 second

            while (retryCount < maxRetries) {
                try {
                    val photoUrls = boatRepository.fetchBoatPhotos(boatName)
                    _boatPhotos.value = ApiState.Success(photoUrls)
                    val photos = photoUrls.mapNotNull { boatPhoto ->
                        boatRepository.fetchPhoto(boatPhoto)
                    }
                    Log.d("API Response", "Boat photos fetched successfully: $photos")
                    _boatImages.value = ApiState.Success(photos)
                    break // Exit the loop if successful
                } catch (e: Exception) {
                    Log.e("API Error", "Failed to fetch boat photos: ${e.message}")
                    _boatPhotos.value = ApiState.Error(e.message ?: "Unknown Error")
                    _boatImages.value = ApiState.Error(e.message ?: "Unknown Error")
                    retryCount++
                    if (retryCount < maxRetries) {
                        delay(retryDelay) // Wait before retrying
                    }
                }
            }
        }
    }

    fun getBoatsByPort(port: String) {
        viewModelScope.launch {
            try {
                val result = boatRepository.fetchBoatsByPort(port)
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
