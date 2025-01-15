package lab.android.chartersapp.charters.presentation.chatWindow

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.dataclasses.Chat
import lab.android.chartersapp.charters.presentation.searchBar.ChatsViewModel
import androidx.compose.runtime.livedata.observeAsState
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Message


@Composable
fun ChatWindowScreen(
    navController: NavController,
    title: String,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val messagesState = remember { mutableStateOf<ApiState<List<Message>>>(ApiState.Loading) }
    var message by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Fetch messages once when the screen loads or title changes
    LaunchedEffect(title) {
        messagesState.value = ApiState.Loading
        try {
            val apiState = viewModel.getMessages(title).value
            when (apiState) {
                is ApiState.Success -> {
                    messagesState.value = ApiState.Success(apiState.data ?: emptyList())
                }
                is ApiState.Error -> {
                    messagesState.value = ApiState.Error(apiState.message)
                }
                else -> {
                    messagesState.value = ApiState.Error("Unknown Error")
                }
            }
        } catch (e: Exception) {
            messagesState.value = ApiState.Error(e.message ?: "Unknown Error")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFF4))
            .padding(8.dp)
    ) {
        Text(
            text = "Chat with $title",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(8.dp)
        )

        when (val state = messagesState.value) {
            is ApiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is ApiState.Success -> {
                val messages = state.data
                if (messages.isEmpty()) {
                    Text(
                        text = "No messages in this chat",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)
                    ) {
                        messages.forEach { msg ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.medium)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = msg.content,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is ApiState.Error -> {
                Text(
                    text = "Error fetching messages: ${state.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (message.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.createMessage(
                                chatTitle = title,
                                content = message,
                                onSuccess = { message = "" },
                                onError = { error -> Log.e("ChatWindowScreen", "Failed to send message: $error") }
                            )
                        }
                    }
                }
            ) {
                Text("Send")
            }
        }
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }
    }
}


