package com.leo.unipiplishopping.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun ArtworkDetailedView(
    artworkModel: ArtworkModel?,
    navController: NavHostController
) {
    val context = LocalContext.current
    var isZoomed by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val imageModifier = Modifier
        .fillMaxWidth()
        .semantics {
            if (artworkModel != null) {
                contentDescription = artworkModel.desc.toString()
            }
        }
        .then(
            if (isZoomed) {
                Modifier
                    .fillMaxHeight() // Fill the height of the screen when zoomed
                    .horizontalScroll(scrollState) // Allow vertical scrolling when zoomed
            } else {
                Modifier
            }
        )
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    isZoomed = !isZoomed // Toggle zoom state on double tap
                }
            )
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(88.dp)
                .clickable {
                    navController.navigate("home_screen")
                }
                .align(Alignment.TopEnd)
                .padding(top = 32.dp)
                .semantics {
                    if (artworkModel != null) {
                        contentDescription = artworkModel.desc.toString()
                    }
                },
        )

        Column (
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.White.copy(alpha = 0.1f))
                .fillMaxWidth()
        ) {
            if (artworkModel != null) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 12.dp,
                            start = 8.dp
                        ),
                    text = artworkModel.title.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
            }
            if (artworkModel != null) {
                Text(
                    modifier = Modifier
                        .padding(
                            bottom = 64.dp,
                            start = 8.dp
                        ),
                    text = artworkModel.desc.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                )
            }
        }

        if (artworkModel != null) {
            AsyncImage(
                model = artworkModel.url,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}
