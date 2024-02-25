
package com.example.chatapptrial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapptrial.ui.theme.ChatAppTrialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTrialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


//                    ChatScreen()
                    //define viewmodel instance here
                    val viewModel = viewModel<ChatViewModel>()
                    ChatScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun ChatScreen(viewModel: ChatViewModel) {


    val messageList by viewModel.messages.collectAsState(initial = emptyList()) // Provide initial value
    val messageText by viewModel.messageText.collectAsState()
    
    Column {
        LazyColumn(reverseLayout = true) { // Scrollable message list
            items(messageList.asReversed()) { message ->
                // Display message UI based on sender/receiver
                MessageItem(message)
            }
        }
        Row {
            OutlinedTextField(
                value = messageText,
                onValueChange = { viewModel.onMessageTextChange(it) },
                label = { Text("Message") }
            )

            Button(onClick = { viewModel.sendMessage() }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {

    val fontStyle = if (message.sender == "Me") FontStyle.Normal else FontStyle.Italic
    val fontSize = if (message.sender == "Me") 20.sp else 18.sp

    Text(
        text = message.text,
        fontStyle = fontStyle,
        fontSize = fontSize
    )
}

