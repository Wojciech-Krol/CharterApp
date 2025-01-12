package lab.android.chartersapp.charters.presentation.searchBar

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.Chat

@Composable
fun ChatsScreen(navController: NavController, viewModel: ChatsViewModel = hiltViewModel()) {
    val chatsState by viewModel.chats.observeAsState(ApiState.Loading)

    LaunchedEffect(Unit) {
        viewModel.getChats()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (chatsState) {
            is ApiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiState.Success<*> -> {
                val chats = (chatsState as ApiState.Success<List<Chat>>).data
                LazyColumn {
                    items(chats.size) { index ->
                        val chat = chats[index]
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                                .clickable {
                                    val chatJson = Uri.encode(Gson().toJson(chat))
                                    navController.navigate("chat_window/$chatJson")
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = chat.ownerName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = chat.lastMessage,
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666)
                                )
                            }
                        }
                    }
                }
            }
            is ApiState.Error -> {
                val error = (chatsState as ApiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Failed to fetch chats: $error")
                }
            }
        }
    }
}