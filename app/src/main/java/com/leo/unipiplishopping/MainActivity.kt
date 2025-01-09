package com.leo.unipiplishopping

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.leo.unipiplishopping.ui.theme.UnipiPLIShoppingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword("user@example.com", "password123")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("Auth", "Signed in with UID: ${user?.uid}")
                } else {
                    Log.e("Auth", "Authentication failed: ${task.exception}")
                }
            }
        // Initialize Firestore
        val db = FirebaseFirestore.getInstance()

        // Reference to the "messages" collection and "hello-world" document
        val docRef = db.collection("messages").document("hello-world")

        // Fetch the document
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Extract and print the "message" field
                    val message = document.getString("message")
                    Log.d("Firestore", "Message: $message")
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching document", exception)
            }


        enableEdgeToEdge()
        setContent {
            UnipiPLIShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnipiPLIShoppingTheme {
        Greeting("Android")
    }
}