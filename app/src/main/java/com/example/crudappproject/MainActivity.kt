package com.example.crudappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

data class Book(val id: Int, val title: String, val author: String, val published: String)

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
    var author by remember { mutableStateOf("") }
    var published by remember { mutableStateOf("") }
    var editId by remember { mutableStateOf<Int?>(null) }
    val books = remember { mutableStateListOf<Book>() }
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
                    label = { Text("Book Title", color = Color.White) },
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
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author", color = Color.White) },
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
                    value = published,
                    onValueChange = { published = it },
                    label = { Text("Published Date", color = Color.White) },
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
                            books.add(Book(nextId, title, author, published))
                            nextId++
                        } else {
                            val index = books.indexOfFirst { it.id == editId }
                            if (index != -1) {
                                books[index] = Book(editId!!, title, author, published)
                            }
                            editId = null
                        }
                        title = ""
                        author = ""
                        published = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (editId == null) "Add Book" else "Update Book",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(books) { book ->
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
                                    Text(text = "Title: ${book.title}", color = Color.White)
                                    Text(text = "Author: ${book.author}", color = Color.White)
                                    Text(text = "Published: ${book.published}", color = Color.White)
                                }
                                Column {
                                    Button(
                                        onClick = {
                                            title = book.title
                                            author = book.author
                                            published = book.published
                                            editId = book.id
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Text("Edit", color = Color.White)
                                    }
                                    Button(
                                        onClick = { books.remove(book) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
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