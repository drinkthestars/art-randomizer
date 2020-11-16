package com.goofy.goober.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.goofy.goober.api.model.ApiImage
import com.goofy.goober.api.model.ApiObjectResults
import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.Result

private const val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1"
private const val SEARCH_URL = "$BASE_URL/search"
private const val OBJECTS_URL = "$BASE_URL/objects"
private const val QUERY_PARAM = "q"
private const val GEO_PARAM = "geoLocation"
private const val DATE_BEGIN_PARAM = "dateBegin"
private const val DATE_END_PARAM = "dateEnd"

class ApiClient {

    private val hasImagesParam = "hasImages" to "true"

    init {
        FuelManager.instance.basePath = BASE_URL
    }

    suspend fun getObject(
        query: String,
        geo: String,
        startDate: Int,
        endDate: Int
    ): Result<Image> {
        val (result, error) = Fuel.get(
            SEARCH_URL,
            listOf(
                hasImagesParam,
                GEO_PARAM to geo,
                DATE_BEGIN_PARAM to startDate,
                DATE_END_PARAM to endDate,
                QUERY_PARAM to query,
            )
        )
            .awaitObjectResult(moshiDeserializerOf(ApiObjectResults::class.java))

        if (error != null || result == null) {
            println("Error loading search results! ${error?.message}")
            return Result.Fail
        }

        return getObject(result.objectIds.random())
    }

    private suspend fun getObject(objectId: Long): Result<Image> {
        val (result, error) = Fuel.get("$OBJECTS_URL/$objectId")
            .awaitObjectResult(moshiDeserializerOf(ApiImage::class.java))

        if (error != null || result == null) {
            println("Error loading objects! ${error?.message}")
            return Result.Fail
        }

        val imageDetail = Image(
            mainUrl = result.mainUrl,
            smallUrl = result.smallUrl,
            title = result.title,
            artist = result.artist,
        )

        return Result.Success(imageDetail)
    }
}
