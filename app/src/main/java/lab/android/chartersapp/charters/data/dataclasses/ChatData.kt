package lab.android.chartersapp.charters.data.dataclasses


data class ChatsResponse(
        val status: String,
        val chats: List<Chat>
)

data class Chat(
        val id: Int,
        val title: String
)