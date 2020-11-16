package com.goofy.goober.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiObjectResults (
    @Json(name = "total") val total: Int,
    @Json(name = "objectIDs") val objectIds: List<Long>
)
