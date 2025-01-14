package lab.android.chartersapp.charters.presentation.accountPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Charter
import lab.android.chartersapp.charters.data.repositories.CharterRepository
import javax.inject.Inject

@HiltViewModel
class AccountPageViewModel @Inject constructor(
    private val charterRepository: CharterRepository
) : ViewModel() {

    private val _charters = MutableLiveData<ApiState<List<Charter>>>(ApiState.Loading)
    val charters: LiveData<ApiState<List<Charter>>> = _charters

    fun getChartersByUser(userName: String) {
        viewModelScope.launch {
            try {
                val result = charterRepository.fetchChartersByUser(userName)
                Log.d("API Response", "Charters fetched successfully: $result")
                _charters.value = ApiState.Success(result)
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch charters: ${e.message}")
                _charters.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}