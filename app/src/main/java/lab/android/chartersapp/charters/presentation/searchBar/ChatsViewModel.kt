package lab.android.chartersapp.charters.presentation.searchBar

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
            } catch (e: Exception) {
                _chats.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun createChat(title: String, onSuccess: (Chat) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val chat = chatRepository.createChat(title)
                if (chat != null) {
                    onSuccess(chat)
                } else {
                    onError("Failed to create chat")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown Error")
            }
        }
    }

    fun messages(id: Int): Collection<String> {
        return listOf("Message 1", "Message 2", "Message 3") // Mock implementation
    }

    fun createMessage(id: Int, message: String) {
        // Mock implementation, replace with actual data saving logic
    }
}