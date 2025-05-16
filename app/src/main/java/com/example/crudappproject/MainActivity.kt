package com.example.crudappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crudappproject.ui.theme.CRUDAPPPROJECTTheme

data class Book(val id: Int, val title: String, val content: String, val timeStamp: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUDAPPPROJECTTheme {
                BookApp()
            }
        }
    }
}

@Composable
fun BookApp() {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var timeStamp by remember { mutableStateOf("") }
    var editId by remember { mutableStateOf<Int?>(null) }
    val notes = remember { mutableStateListOf<Book>() }
    var nextId by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Color(0xFF000000),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Note Title", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Note Content...", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = timeStamp,
                    onValueChange = { timeStamp = it },
                    label = { Text("Date", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (editId == null) {
                            if(title != "" || content != "" || timeStamp != "") {
                                notes.add(Book(nextId, title, content, timeStamp))
                                nextId++
                            }else{
                                return@Button
                            }
                        } else {
                            val index = notes.indexOfFirst { it.id == editId }
                            if (index != -1) {
                                notes[index] = Book(editId!!, title, content, timeStamp)
                            }
                            editId = null
                        }
                        title = ""
                        content = ""
                        timeStamp = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (editId == null) "Add Note" else "Update Note",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(notes) { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = "Title: ${note.title}", color = Color.White)
                                    Text(text = "Author: ${note.content}", color = Color.White)
                                    Text(text = "Published: ${note.timeStamp}", color = Color.White)
                                }
                                Column {
                                    Button(
                                        onClick = {
                                            title = note.title
                                            content = note.content
                                            timeStamp = note.timeStamp
                                            editId = note.id
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)),
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text("Edit", color = Color.White)
                                    }
                                    Button(
                                        onClick = { notes.remove(note) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text("Delete", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookAppPreview() {
    CRUDAPPPROJECTTheme {
        BookApp()
    }
}