package lab.android.chartersapp.charters.data.dataclasses

data class MessagesResponse(
    val status: String,
    val chats: List<Message>
)

data class Message(
    val chatTitle: String,
    val sequenceNumber: Int,
    val content : String
)