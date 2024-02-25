package com.example.chatapptrial

import android.util.Log
import androidx.lifecycle.ViewModel
import io.socket.client.IO
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel: ViewModel() {
    private val socket = IO.socket("http://192.168.1.7:3000")
    val messages = MutableStateFlow<List<Message>>(emptyList())
    val messageText = MutableStateFlow("")





    init {
        socket.connect()
        if(socket.connected())
        {
            Log.d("my","check1")
        }

        socket.on("receiveMessage") { args ->
            val newMessage = Message(sender = "Other", text = args[0].toString()) // Example
            messages.value = messages.value + newMessage
        }

    }



    fun onMessageTextChange(newText: String) {
        messageText.value = newText
    }

    fun sendMessage() {
        socket.emit("sendMessage", messageText.value)


        Log.d("myApp", messageText.value)
        messageText.value = "" // Clear the message after sending
    }
}
