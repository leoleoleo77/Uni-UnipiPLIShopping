package com.leo.unipiplishopping.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R

@Composable
fun HomeView(
    navController: NavHostController) {
    val artworkData = FirebaseFirestore
        .getInstance()
        .collection(AppConstants.ARTWORK_COLLECTION)
    //var artworkCountState = 0//remember { mutableIntStateOf(0) }
    val artworkIdList = remember { mutableStateListOf<Int>()}
    val homeState = remember { mutableStateOf(AppConstants.NAVIGATION_HOME) }
    val selectedArtworkState = remember { mutableStateOf<ArtworkModel?>(null) }

    LaunchedEffect(Unit) {
        artworkData.get()
            .addOnSuccessListener { querySnapshot ->
                var i = 0;
                while (i < querySnapshot.size()) {
                    artworkIdList.add(i)
                    i++
                }
                artworkIdList.shuffle()
            }
            .addOnFailureListener { exception ->
                Log.e("HomeScreen", "Error fetching artwork count", exception)
            }
    }

    when (homeState.value) {
        AppConstants.NAVIGATION_HOME -> {
            ArtworksFeed(
                artworkIdList = artworkIdList,
                artworkData = artworkData,
                homeState = homeState,
                selectedArtworkState = selectedArtworkState
            )
        }
        AppConstants.NAVIGATION_ARTWORK_DETAILS -> {
            ArtworkDetailedView(
                artworkModel = selectedArtworkState.value,
                homeState = homeState,
            )
        }
        AppConstants.NAVIGATION_PROFILE_DETAILS -> {
            ProfileDetails(homeState = homeState,)
        }
    }
}

@Composable
private fun ArtworksFeed(
    artworkIdList: SnapshotStateList<Int>,
    artworkData: CollectionReference,
    homeState: MutableState<String>,
    selectedArtworkState: MutableState<ArtworkModel?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Header(homeState)
        artworkIdList.forEachIndexed{_, item ->
            ArtworkView(
                artworkRef = artworkData.document(item.toString()),
                homeState = homeState,
                selectedArtworkState = selectedArtworkState,
            )
        }
    }
}

@Composable
private fun Header(
    homeState: MutableState<String>,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.titleLarge
        )
        Box (
            modifier = Modifier
                .align(Alignment.Bottom)
                .clickable {
                    homeState.value = AppConstants.NAVIGATION_PROFILE_DETAILS
                }
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 4.dp)
                    .size(40.dp),
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(R.string.profile_details),
                tint = Color.White,

                )
        }
    }
}
