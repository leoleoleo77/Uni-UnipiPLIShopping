package com.leo.unipiplishopping.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.DocumentReference
import com.leo.unipiplishopping.AppConstants

@Composable
fun ArtworkView(
    artworkRef: DocumentReference,
    homeState: MutableState<String>,
    selectedArtworkState: MutableState<ArtworkModel?>
) {
    val artworkModelState = remember { mutableStateOf<ArtworkModel?>(null) }

    LaunchedEffect(artworkModelState) {
        artworkRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                artworkModelState.value = documentSnapshot.toObject(ArtworkModel::class.java)
            } else {
                Log.w("ArtworkScreen", "Artwork document does not exist")
            }
        }.addOnFailureListener { e ->
            Log.w("ArtworkScreen", "Error getting artwork document", e)
        }
    }
    // Display artwork details
    artworkModelState.value?.let { model ->
        model.url?.let { url ->
            Box {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            homeState.value = AppConstants.HOME_ARTWORK_DETAIL
                            selectedArtworkState.value = model
                        }
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