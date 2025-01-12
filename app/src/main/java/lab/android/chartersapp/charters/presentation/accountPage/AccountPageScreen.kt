package lab.android.chartersapp.charters.presentation.accountPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import lab.android.chartersapp.R

@Composable
fun AccountPageScreen(viewModel: AccountPageViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account Icon",
            modifier = Modifier.height(256.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = "user",
            supportingText = { Text("Supporting text") },
            onValueChange = {},
            label = { Text("User") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = "password",
            supportingText = { Text("Supporting text") },
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Charter history",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            )
        )
    }
}