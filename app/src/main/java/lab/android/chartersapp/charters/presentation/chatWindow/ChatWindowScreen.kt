package lab.android.chartersapp.charters.presentation.chatWindow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.launch
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.Chat
import lab.android.chartersapp.charters.presentation.searchBar.ChatsViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatWindowScreen(navController: NavController, chatJson: String, viewModel: ChatsViewModel = hiltViewModel()) {
    val chat = remember { Gson().fromJson(chatJson, Chat::class.java) }
    var message by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val dateFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    val chatsState by viewModel.chats.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    val mockMessages = remember { mutableStateListOf<Pair<String, Boolean>>() }

    LaunchedEffect(chatsState) {
        val updatedChat = (chatsState as? ApiState.Success<List<Chat>>)?.data?.find { it.id == chat.id }
        if (updatedChat != null) {
            mockMessages.clear()
            mockMessages.addAll(updatedChat.lastMessage.split("\n").map { it to false })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFF4)) // Light gray background
            .padding(8.dp)
    ) {
        Text(
            text = "Chat with ${chat.ownerName}",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(8.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp)
        ) {
            mockMessages.forEach { (msg, isUser) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (isUser) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0), // Blue for user, gray for others
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    color = if (isUser) Color.White else Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dateFormat.format(Date()),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = if (isUser) Color(0xFFCCE7FF) else Color(0xFF757575)
                                ),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
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
                placeholder = { Text("Type a message") },
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (message.isNotBlank()) {
                        coroutineScope.launch {
                            mockMessages.add(message to true)
                            mockMessages.add("Got it!" to false) // Simulate response
                            message = ""
                            scrollState.animateScrollTo(scrollState.maxValue)
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
            Text(text = "Back")
        }
    }

    LaunchedEffect(key1 = mockMessages.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
}