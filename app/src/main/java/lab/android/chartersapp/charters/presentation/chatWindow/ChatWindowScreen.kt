package lab.android.chartersapp.charters.presentation.chatWindow

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

@Composable
fun ChatWindowScreen(
    navController: NavController,
    chatJson: String,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val chatData = remember { Gson().fromJson(chatJson, Chat::class.java) }
    var message by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Fetch messages for the current chat
    val messages = remember { mutableStateListOf<String>() }

    LaunchedEffect(chatData.id) {
        messages.clear()
        messages.addAll(viewModel.messages(chatData.id))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFF4))
            .padding(8.dp)
    ) {
        Text(
            text = "Chat with top",
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
                            text = msg,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )
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
                            messages.add(message)
                            viewModel.createMessage(chatData.id, message)
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
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Back")
        }

    }
}

