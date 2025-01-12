package lab.android.chartersapp.charters.data

import javax.inject.Inject

class ChatRepository @Inject constructor() {

    fun fetchChats(): List<Chat> {
        return listOf(
            Chat(id = 1, ownerName = "Owner 1", lastMessage = "Hello, how can I help you?", timestamp = System.currentTimeMillis()),
            Chat(id = 2, ownerName = "Owner 2", lastMessage = "I need information about boat rentals.", timestamp = System.currentTimeMillis())
        )
    }
}