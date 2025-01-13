package com.leo.unipiplishopping.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthUtils {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun attemptLogin(email: String, pass: String): AuthResult {
        return try {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Await the result of the sign-in task
                auth.signInWithEmailAndPassword(email, pass).await()
                AuthResult.Success
            } else {
                Log.e("Auth", "Email or password is empty")
                AuthResult.Error
            }
        } catch (e: Exception) {
            Log.e("Auth", "Authentication failed: ${e.message}")
            AuthResult.Fail
        }
    }
}

sealed class AuthResult {
    data object Success : AuthResult()
    data object Fail : AuthResult()
    data object Error : AuthResult()
    data object Unknown : AuthResult()
}

