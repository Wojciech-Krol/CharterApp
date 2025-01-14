package lab.android.chartersapp.charters.presentation.loginPage

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.d("LoginPageViewModel", "login: $username, $password")

            try {
                val result = authRepository.login(username, password)
                _loginResult.value = result
            } catch (e: Exception) {
                _loginResult.value = false
            }
        }
    }
}