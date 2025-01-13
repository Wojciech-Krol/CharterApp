package lab.android.chartersapp.charters.data.repositories

import javax.inject.Inject
import android.util.Log
import lab.android.chartersapp.charters.data.dataclasses.Chat
import lab.android.chartersapp.charters.data.ChatsApiService
import lab.android.chartersapp.charters.data.dataclasses.Port
import retrofit2.Response

class ChatRepository @Inject constructor(private val apiService: ChatsApiService) {

    suspend fun fetchChats(): List<Chat> {
        val response = apiService.getAllChats()
        if (response.isSuccessful) {
            response.body()?.let {
                return it.chats
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch ports, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun createChat(title: String): Chat? {
        return try {
            val response: Response<Chat> = apiService.createChat(title)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ChatRepository", "Error creating chat: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChatRepository", "Exception creating chat: ${e.message}")
            null
        }
    }
}