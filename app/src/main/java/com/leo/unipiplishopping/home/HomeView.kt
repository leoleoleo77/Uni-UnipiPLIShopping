package com.leo.unipiplishopping.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R
import com.leo.unipiplishopping.authentication.AuthUtils
import com.leo.unipiplishopping.home.utils.ArtworkModel
import com.leo.unipiplishopping.home.utils.ShopNotificationHandler
import com.leo.unipiplishopping.home.utils.areLocationPermissionsGranted
import com.leo.unipiplishopping.home.utils.areNotificationPermissionsGranted
import java.util.Locale

@SuppressLint("InlinedApi")
@Composable
fun HomeView(
    authAgent: AuthUtils,
    deepLinkArtworkId: String?,
    toggleDarkMode: () -> Unit,
    updateAppLocale: (Locale) -> Unit,
    navController: NavHostController
) {
    val artworkCollection = authAgent.getArtworkCollection()
    val artworkIdList = remember { mutableStateListOf<Int>()}
    val artworkLocationMap = remember { mutableMapOf<String, GeoPoint>() }
    val homeState = remember { mutableStateOf(AppConstants.NAVIGATION_HOME) }
    val selectedArtworkState = remember { mutableStateOf<ArtworkModel?>(null) }
    val selectedArtworkId = remember { mutableIntStateOf(0) }
    val shouldShowNotification = deepLinkArtworkId == null

    // If deepLinkArtworkId != null then the activity was created by a deeplink
    // so open the corresponding artwork detailed view
    HandleDeepLink(
        deepLinkArtworkId = deepLinkArtworkId,
        homeState = homeState,
        artworkCollection = artworkCollection,
        selectedArtworkState = selectedArtworkState
    )

    RequestPermissions()
    ShopNotificationHandler(artworkLocationMap, shouldShowNotification)
    FetchArtworkList(artworkCollection, artworkIdList)

    when (homeState.value) {
        AppConstants.NAVIGATION_HOME -> {
            ArtworksFeed(
                artworkIdList = artworkIdList,
                artworkLocationMap = artworkLocationMap,
                artworkData = artworkCollection,
                homeState = homeState,
                selectedArtworkState = selectedArtworkState,
                selectedArtworkId = selectedArtworkId,
            )
        }
        AppConstants.NAVIGATION_ARTWORK_DETAILS -> {
            ArtworkDetailedView(
                artworkModel = selectedArtworkState.value,
                artworkId = selectedArtworkId.intValue,
                homeState = homeState,
                authAgent = authAgent
            )
        }
        AppConstants.NAVIGATION_PROFILE_DETAILS -> {
            ProfileDetails(
                homeState = homeState,
                authAgent = authAgent,
                toggleDarkMode = toggleDarkMode,
                updateAppLocale = updateAppLocale,
                navController = navController,
            )
        }
    }
}

// Gets the artwork collection from Firestore asynchronously
// also shuffles the list to spice up the user experience
@Composable
private fun FetchArtworkList(
    artworkCollection: CollectionReference,
    artworkIdList: SnapshotStateList<Int>
) {
    LaunchedEffect(Unit) {
        artworkCollection.get()
            .addOnSuccessListener { querySnapshot ->
                var i = 0
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
}


@Composable
private fun ArtworksFeed(
    artworkIdList: SnapshotStateList<Int>,
    artworkLocationMap: MutableMap<String, GeoPoint>,
    artworkData: CollectionReference,
    homeState: MutableState<String>,
    selectedArtworkState: MutableState<ArtworkModel?>,
    selectedArtworkId: MutableState<Int>
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
                artworkLocationMap = artworkLocationMap,
                homeState = homeState,
                selectedArtworkState = selectedArtworkState,
                selectedArtworkId = selectedArtworkId,
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
            color = MaterialTheme.colorScheme.primary,
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
                tint = MaterialTheme.colorScheme.primary,

                )
        }
    }
}

@Composable
private fun HandleDeepLink(
    deepLinkArtworkId: String?,
    homeState: MutableState<String>,
    artworkCollection: CollectionReference,
    selectedArtworkState: MutableState<ArtworkModel?>
) {
    if (deepLinkArtworkId == null) return

    LaunchedEffect(deepLinkArtworkId) {
        artworkCollection.document(deepLinkArtworkId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    selectedArtworkState.value = documentSnapshot.toObject(ArtworkModel::class.java)
                    homeState.value = AppConstants.NAVIGATION_ARTWORK_DETAILS
                } else {
                    Log.w("HomeView", "Artwork document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.w("HomeView", "Error getting artwork document", e)
            }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestPermissions() {
    if (areNotificationPermissionsGranted(LocalContext.current) ||
        areLocationPermissionsGranted(LocalContext.current)) return
    // Create a stateful launcher using rememberLauncherForActivityResult
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    // Launch the permission request on composition
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            )
        )
    }
}