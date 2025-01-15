package lab.android.chartersapp.charters.data.repositories

import javax.inject.Inject
import android.util.Log
import lab.android.chartersapp.charters.data.MessagesApiService
import lab.android.chartersapp.charters.data.dataclasses.Message
import lab.android.chartersapp.charters.data.dataclasses.MessagesResponse

class MessageRepository @Inject constructor(private val apiService: MessagesApiService) {

    suspend fun fetchMessages(chatTitle: String): List<Message> {
        val response = apiService.getMessages(chatTitle)
        if (response.isSuccessful) {
            val body = response.body()
            return body?.chats ?: emptyList() // Return an empty list if chats is null
        }
        throw Exception("Failed to fetch messages, response code: ${response.code()}, message: ${response.message()}")
    }


    suspend fun sendMessage(chatTitle: String, content: String): Boolean {
        try {
            Log.d("MessageRepository", "URL: ${apiService.toString()}") // Verify the base URL
            Log.d("MessageRepository", "Sending message to: chatTitle=$chatTitle, content=$content")
            val response = apiService.sendMessage(chatTitle, content)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("MessageRepository", "Response body: $responseBody")
                if (responseBody is Map<*, *> && responseBody["status"] == "success") {
                    Log.d("MessageRepository", "Message sent: $content")
                    return true
                }
                throw Exception("Unexpected response: $responseBody")
            } else {
                Log.e("MessageRepository", "API error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("MessageRepository", "Error sending message: ${e.message}")
            return false
        }
        return false
    }
}