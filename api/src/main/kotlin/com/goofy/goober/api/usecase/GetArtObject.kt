package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.Result

class GetArtObject(
    private val apiClient: ApiClient
) {

    suspend operator fun invoke(
        query: String,
        geo: String,
        startDate: Int,
        endDate: Int
    ): Result<Image> {
        return apiClient.getObject(
            query = query,
            geo = geo,
            startDate = startDate,
            endDate = endDate
        )
    }
}
