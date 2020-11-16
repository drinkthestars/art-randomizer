package com.goofy.goober.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiImage (
    @Json(name = "primaryImage") val mainUrl: String,
    @Json(name = "primaryImageSmall") val smallUrl: String,
    @Json(name = "title") val title: String,
    @Json(name = "artistDisplayName") val artist: String
)
