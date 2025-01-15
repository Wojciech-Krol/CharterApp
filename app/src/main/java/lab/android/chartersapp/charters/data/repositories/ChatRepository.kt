package lab.android.chartersapp.charters.data.repositories

import javax.inject.Inject
import android.util.Log
import lab.android.chartersapp.charters.data.dataclasses.Chat
import lab.android.chartersapp.charters.data.ChatsApiService
import lab.android.chartersapp.charters.data.dataclasses.Port
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import org.json.JSONObject

class ChatRepository @Inject constructor(private val apiService: ChatsApiService) {

    suspend fun fetchChats(): List<Chat> {
        val response = apiService.getAllChats()
        if (response.isSuccessful) {
            response.body()?.let {
                return it.chats
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch chats, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun createChat(title: String): Boolean {
        try {
            Log.d("ChatRepository", "Creating chat: $title")

            val response = apiService.createChat(title)

            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("ChatRepository", "Response body: $responseBody")

                if (responseBody is Map<*, *> && responseBody["status"] == "success") {
                    Log.d("ChatRepository", "Chat created: $title")
                    return true
                }

                throw Exception("Unexpected response: $responseBody")
            } else {
                Log.e("ChatRepository", "API error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("ChatRepository", "Error creating chat: ${e.message}")
            return false
        }
        return false
    }


}
