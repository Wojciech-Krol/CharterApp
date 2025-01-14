package lab.android.chartersapp.charters.presentation.searchBar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Chat
import lab.android.chartersapp.charters.data.repositories.ChatRepository
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(private val chatRepository: ChatRepository) : ViewModel() {

    private val _chats = MutableLiveData<ApiState<List<Chat>>>(ApiState.Loading)
    val chats: LiveData<ApiState<List<Chat>>> = _chats

    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch {
            try {
                val chats = chatRepository.fetchChats()
                _chats.value = ApiState.Success(chats)
                Log.d("ChatsViewModel", "Fetched chats: $chats")
            } catch (e: Exception) {
                _chats.value = ApiState.Error(e.message ?: "Unknown Error")
                Log.e("ChatsViewModel", "Error fetching chats: ${e.message}")
            }
        }
    }

    fun createChat(title: String, onSuccess: (Chat) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = chatRepository.createChat(title)
                if (success != null) {
                    onSuccess(Chat(title = title)) // Assuming Chat has a constructor that takes a title
                    Log.d("ChatsViewModel", "Chat created: $title")
                    // Fetch the updated list of chats
                    getChats()
                } else {
                    onError("Failed to create chat")
                    Log.e("ChatsViewModel", "Failed to create chat")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown Error")
                Log.e("ChatsViewModel", "Error creating chat: ${e.message}")
            }
        }
    }

    fun messages(id: Int): Collection<String> {
        return listOf("Message 1", "Message 2", "Message 3") // Mock implementation
    }

    fun createMessage(title: String, message: String) {
        // Mock implementation, replace with actual data saving logic
    }
}