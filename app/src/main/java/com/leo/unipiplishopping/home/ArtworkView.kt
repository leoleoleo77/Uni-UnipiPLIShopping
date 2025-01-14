package com.leo.unipiplishopping.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ArtworkView(
    dataBase: FirebaseFirestore,
    artworkId: Int
) {
    val artworkModelState = remember { mutableStateOf<ArtworkModel?>(null) }
//    val artworkState = remember {
//        mutableStateOf(ArtworkState.Loading) }


//    if (artworkState.value == ArtworkState.Loading) {
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.tertiary)
//                .fillMaxWidth()
//                .height(200.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    }

    LaunchedEffect(artworkModelState) {
        val artworkRef = dataBase
            .collection("artwork")
            .document(artworkId.toString())

        artworkRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                artworkModelState.value = documentSnapshot.toObject(ArtworkModel::class.java)
            } else {
                // Handle case where document doesn't exist
                Log.w("ArtworkScreen", "Artwork document does not exist")
            }
        }.addOnFailureListener { exception ->
            // Handle errors
            Log.w("ArtworkScreen", "Error getting artwork document", exception)
        }
    }
    // Display artwork details
    artworkModelState.value?.let { model ->
        model.url?.let { url ->
            Box {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                // Add a chip in the top-left corner
                model.price?.let { price ->
                    Surface(
                        color = Color.Black.copy(alpha = 0.7f), // Semi-transparent background
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = "${price.toInt()} €",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        } // ?: fail to load image
    }
}

enum class ArtworkState {
    Loading,
    Success,
    Fail
}