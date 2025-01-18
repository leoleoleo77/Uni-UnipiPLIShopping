package com.leo.unipiplishopping.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiplishopping.AppConstants
import kotlinx.coroutines.tasks.await

class AuthUtils {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getArtworkCollection(): CollectionReference {
        return db.collection(AppConstants.ARTWORK_COLLECTION)
    }

    fun getPurchasesCollection(): CollectionReference {
        return db.collection(AppConstants.PURCHASES_COLLECTION)
    }

    suspend fun attemptLogin(email: String, pass: String): AuthResult {
        return try {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Await the result of the sign-in task
                auth.signInWithEmailAndPassword(email, pass).await()
                AuthResult.Success
            } else {
                Log.e("Auth", "Email or password is empty")
                AuthResult.Fail
            }
        } catch (e: Exception) {
            Log.e("Auth", "Authentication failed: ${e.message}")
            AuthResult.Fail
        }
    }

    suspend fun attemptRegister(
        name: String,
        email: String,
        password: String
    ): AuthResult {
        if (name.isEmpty()) return AuthResult.Fail

        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            // Update the user's display name
            val user = result.user
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user?.updateProfile(profileUpdates)?.await()

            Log.e("Auth","User registered successfully with name: $name")
            AuthResult.Success
        } catch (e: Exception) {
            Log.e("Auth","Register failed ${e.message}")
            AuthResult.Fail
        }
    }
}

sealed class AuthResult {
    data object Success : AuthResult()
    data object Fail : AuthResult()
    data object Unknown : AuthResult()
}

