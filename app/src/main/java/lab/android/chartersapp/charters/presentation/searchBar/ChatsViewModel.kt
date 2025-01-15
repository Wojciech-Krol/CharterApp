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
import lab.android.chartersapp.charters.data.dataclasses.Message
import lab.android.chartersapp.charters.data.repositories.MessageRepository

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _chats = MutableLiveData<ApiState<List<Chat>>>(ApiState.Loading)
    val chats: LiveData<ApiState<List<Chat>>> = _chats

    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch {
            try {
                Log.d("ChatsViewModel", "Fetching chats")
                val fetchedChats = chatRepository.fetchChats()
                Log.d("ChatsViewModel", "Fetched chats: $fetchedChats")
                _chats.value = ApiState.Success(fetchedChats)
            } catch (e: Exception) {
                Log.e("ChatsViewModel", "Error fetching chats: ${e.message}")
                _chats.value = ApiState.Error(e.message ?: "Unknown Error")
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

    fun getMessages(chatTitle: String): LiveData<ApiState<List<Message>>> {
        val messagesLiveData = MutableLiveData<ApiState<List<Message>>>(ApiState.Loading)
        viewModelScope.launch {
            try {
                Log.d("ChatsViewModel", "Fetching messages for chat: $chatTitle")
                val messages = messageRepository.fetchMessages(chatTitle)
                Log.d("ChatsViewModel", "Fetched messages: $messages")
                messagesLiveData.value = ApiState.Success(messages)
            } catch (e: Exception) {
                Log.e("ChatsViewModel", "Error fetching messages: ${e.message}")
                messagesLiveData.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
        return messagesLiveData
    }



    fun createMessage(chatTitle: String, content: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("ChatsViewModel", "Sending message: '$content' to chat: $chatTitle")
                val success = messageRepository.sendMessage(chatTitle, content)
                if (success) {
                    Log.d("ChatsViewModel", "Message sent successfully: '$content'")
                    onSuccess()
                } else {
                    Log.e("ChatsViewModel", "Failed to send message")
                    onError("Failed to send message")
                }
            } catch (e: Exception) {
                Log.e("ChatsViewModel", "Error sending message: ${e.message}")
                onError(e.message ?: "Unknown Error")
            }
        }
    }

}