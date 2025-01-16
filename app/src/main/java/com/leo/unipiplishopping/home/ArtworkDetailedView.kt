package com.leo.unipiplishopping.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R
import com.leo.unipiplishopping.components.DivaCloseButton

@Composable
fun ArtworkDetailedView(
    artworkModel: ArtworkModel?,
    homeState: MutableState<String>,
) {
    if (artworkModel == null) {
        homeState.value = AppConstants.NAVIGATION_HOME
        return
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        DivaCloseButton(homeState)
        Artwork(artworkModel)
        BuyButton(artworkModel) {

        }
    }
}

@Composable
private fun Artwork(artworkModel: ArtworkModel) {
    Column {
        AsyncImage(
            model = artworkModel.url,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Caption(artworkModel)
    }

}

@Composable
private fun Caption(artworkModel: ArtworkModel) {

    @Composable
    fun Title() {
        val artworkName = artworkModel.title
        val artworkDate = artworkModel.date
        val title = "$artworkName • $artworkDate"

        Text(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 8.dp
                ),
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
    }

    @Composable
    fun Description() {
        val artworkArtist = artworkModel.artist
        val artworkDescription =
            artworkModel.description ?: ""
        val description = "$artworkArtist, $artworkDescription"

        Text(
            modifier = Modifier
                .padding(
                    bottom = 12.dp,
                    start = 8.dp
                ),
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Column (
        modifier = Modifier
            .background(
                MaterialTheme
                    .colorScheme
                    .primary
                    .copy(alpha = 0.1f)
            )
            .fillMaxWidth(),
    ) {
        Title()
        Description()
    }
}

@Composable
private fun BuyButton(
    artworkModel: ArtworkModel,
    onClick: () -> Unit
) {
    val buyNowText = stringResource(id = R.string.buy_label)
    val artworkPrice = artworkModel.price?.toInt()
    val buttonText = "$buyNowText $artworkPrice €"

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp),
            text = buttonText)
    }
}
