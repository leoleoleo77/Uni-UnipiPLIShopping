package com.leo.unipiplishopping.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthUtils {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun attemptLogin(email: String, pass: String): AuthResult {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            val loginAttempt = auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("Auth", "Signed in with UID: ${user?.uid}")
                    } else {
                        Log.e("Auth", "Authentication failed: ${task.exception}")
                    }
                }
            return if (loginAttempt.isSuccessful) {
                AuthResult.Success
            } else {
                AuthResult.Fail
            }
        } else {
            Log.e("Auth", "Authentication Error")
            return AuthResult.Error
        }
    }
}

enum class AuthResult {
    Success,
    Fail,
    Error,
    Unknown
}
