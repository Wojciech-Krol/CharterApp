package lab.android.chartersapp.charters.presentation.searchBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.*
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@HiltViewModel
class ChatsViewModel @Inject constructor() : ViewModel() {

    private val _chats = MutableLiveData<ApiState<List<Chat>>>(ApiState.Loading)
    val chats: LiveData<ApiState<List<Chat>>> = _chats

    fun getChats() {
        viewModelScope.launch {
            try {
                val mockChats = listOf(
                    Chat(id = 1, ownerName = "Owner 1", lastMessage = "Hello, how can I help you?", timestamp = System.currentTimeMillis()),
                    Chat(id = 2, ownerName = "Owner 2", lastMessage = "I need information about boat rentals.", timestamp = System.currentTimeMillis())
                )
                _chats.value = ApiState.Success(mockChats)
            } catch (e: Exception) {
                _chats.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}