package lab.android.chartersapp.charters.data

data class Chat(
        val id: Int,
        val ownerName: String,
        val lastMessage: String,
        val timestamp: Long,
)