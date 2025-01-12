package lab.android.chartersapp.charters.presentation.searchBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.Chat
import lab.android.chartersapp.charters.data.ChatRepository
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lab.android.chartersapp.charters.data.ApiState

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chats = MutableLiveData<ApiState<List<Chat>>>(ApiState.Loading)
    val chats: LiveData<ApiState<List<Chat>>> = _chats

    fun getChats() {
        viewModelScope.launch {
            try {
                _chats.value = ApiState.Success(chatRepository.fetchChats())
            } catch (e: Exception) {
                _chats.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun createChat(ownerName: String): Chat {
        val newChat = Chat(
            id = chatRepository.fetchChats().size + 1,
            ownerName = ownerName,
            lastMessage = "",
            timestamp = System.currentTimeMillis()
        )
        chatRepository.addChat(newChat)
        getChats() // Refresh chat list
        return newChat
    }

    fun getChatById(chatId: Int): Chat? = chatRepository.getChatById(chatId)
}
