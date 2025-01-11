package com.leo.unipiplishopping

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.leo.unipiplishopping.authentication.AuthenticationView
import com.leo.unipiplishopping.ui.theme.DivaTheme
import java.util.Locale


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
            val locale = Locale("en")
            updateLocale(locale)
            DivaTheme {
                MyApp()
            }
//            UnipiPLIShoppingTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }

    private fun updateLocale(locale: Locale) {
        val config = resources.configuration
        val updatedConfig = Configuration(config)
        updatedConfig.setLocale(locale)

        // Apply the new configuration to the context
        createConfigurationContext(updatedConfig)
        resources.updateConfiguration(updatedConfig, resources.displayMetrics)
    }
}



@Composable
fun LoginScreen(navController: NavHostController) {
    // Replace with your UI design for the login screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen")
        Button(onClick = { navController.navigate("home") }) {
            Text("Login")
        }
    }
}

@Composable
fun HomeScreen() {
    // Replace with your UI design for the home screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen")
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApp()
//        }
//    }
//}

@Composable
fun MyApp() {
    // Material theme applied
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") { AuthenticationView() }
            composable("home") { HomeScreen() }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    UnipiPLIShoppingTheme {
//        Greeting("Android")
//    }
//}