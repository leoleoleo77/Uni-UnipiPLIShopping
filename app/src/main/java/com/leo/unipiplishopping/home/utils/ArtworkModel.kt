package com.leo.unipiplishopping.home.utils

import com.google.firebase.firestore.GeoPoint

data class ArtworkModel (
    val artist: String? = null,
    val date: Int? = null,
    val description: String? = null,
    val price: Double? = null,
    val shop: GeoPoint? = null,
    val title: String? = null,
    val url: String? = null
)
