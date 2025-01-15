package com.leo.unipiplishopping.home

import com.google.firebase.firestore.GeoPoint

data class ArtworkModel (
    val id: Int? = null,
    val artist: String? = null,
    val date: Int? = null,
    val desc: String? = null,
    val price: Double? = null,
    val shop: GeoPoint? = null,
    val title: String? = null,
    val url: String? = null
)
