package com.leo.unipiplishopping.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R

@Composable
fun HomeView(
    navController: NavHostController) {
    val artworkData = FirebaseFirestore
        .getInstance()
        .collection(AppConstants.ARTWORK_COLLECTION)
    val artworkCountState = remember { mutableIntStateOf(0) }
    val homeState = remember { mutableStateOf(AppConstants.HOME_DEFAULT)}
    val selectedArtworkState = remember { mutableStateOf<ArtworkModel?>(null) }

    // Fetch the artwork count from Firestore
    LaunchedEffect(Unit) {
        artworkData.get()
            .addOnSuccessListener { querySnapshot ->
                artworkCountState.intValue = querySnapshot.size()
            }
            .addOnFailureListener { exception ->
                Log.e("HomeScreen", "Error fetching artwork count", exception)
            }
    }

    if (homeState.value == AppConstants.HOME_DEFAULT) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
        ) {
            item {
                HomeTitle()
            }
            items(artworkCountState.intValue) { index ->
                ArtworkView(
                    artworkRef = artworkData.document(index.toString()),
                    homeState = homeState,
                    selectedArtworkState = selectedArtworkState,
                )
            }
        }
    } else {
        ArtworkDetailedView(
            artworkModel = selectedArtworkState.value,
            navController = navController)
    }
}

@Composable
private fun HomeTitle() {
    Row {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 32.dp),
            textAlign = TextAlign.Start,
            text = stringResource(id = R.string.home_title),
            style = MaterialTheme.typography.titleLarge
        )
        //Icon(Icons.Default, null)
    }
}