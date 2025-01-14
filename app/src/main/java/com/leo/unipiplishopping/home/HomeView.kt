package com.leo.unipiplishopping.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiplishopping.R

@Composable
fun HomeScreen() {
    val db = FirebaseFirestore.getInstance()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        HomeTitle()
        ArtworkView(db, 0)
        ArtworkView(db, 1)
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