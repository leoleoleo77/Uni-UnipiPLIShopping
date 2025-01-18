package com.leo.unipiplishopping.home.Utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.MainActivity
import com.leo.unipiplishopping.R
import kotlinx.coroutines.delay

private const val TEN_SECONDS: Long = 10000
private const val THREE_SECONDS: Long = 3000
@Composable
fun ShopNotificationHandler(
    artworkLocationMap: MutableMap<String, GeoPoint>
) {
    val thisContext = LocalContext.current
    val fusedLocationProviderClient
        = LocationServices.getFusedLocationProviderClient(thisContext)

    val title = stringResource(id = R.string.notification_title)
    val desc1 = stringResource(id = R.string.notification_description1)
    val desc2 = stringResource(id = R.string.notification_description2)

    fun getClosestArtwork(userCoordinates: Pair<Double, Double>) {
        var closestArtworkId: String? = null
        var shortestDistance: Double? = null
        var shopCoordinates: Pair<Double, Double>

        artworkLocationMap.forEach{ entry ->
            entry.value.let { geoPoint ->
                shopCoordinates = Pair(geoPoint.latitude, geoPoint.longitude)
            }
            val distance = FloatArray(1)
            Location.distanceBetween(
                userCoordinates.first,
                userCoordinates.second,
                shopCoordinates.first,
                shopCoordinates.second,
                distance,
            )

            val currentDistance = distance.first().toDouble()
            if (shortestDistance == null ||
                shortestDistance!! > currentDistance
            ) {
                shortestDistance = currentDistance
                closestArtworkId = entry.key
            }
        }

        closestArtworkId?.let {
            val distance = shortestDistance?.toInt()

            createNotificationChannel(thisContext)
            showNotification(
                thisContext,
                title = title,
                message= "$desc1 $distance $desc2",
                artworkId = it
            )
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(THREE_SECONDS)
            if (artworkLocationMap.isNotEmpty()) {
                getCurrentLocation(
                    fusedLocationProviderClient = fusedLocationProviderClient,
                    context = thisContext,
                    onGetCurrentLocationSuccess = { userCoordinates ->
                        getClosestArtwork(userCoordinates)
                    },
                    onGetCurrentLocationFailed = { e ->
                        Log.e("Get current location error: ", e.toString())
                    }
                )
                break
            } else {
                continue;
            }
        }
    })
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermission(onGranted: () -> Unit) {
    if (areNotificationPermissionsGranted(LocalContext.current)) return
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

@SuppressLint("InlinedApi")
private fun areNotificationPermissionsGranted(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context, Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
private fun showNotification(
    context: Context,
    title: String,
    message: String,
    artworkId: String
) {
    val channelId = AppConstants.CHANNEL_ID

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(AppConstants.DEEPLINK_KEY, artworkId)
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setContentIntent(pendingIntent) // Attach the PendingIntent
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(1, notification)
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = AppConstants.CHANNEL_ID
        val channelName = AppConstants.CHANNEL_NAME
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)

        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}