package com.agelousis.noteitdown.network.apis

import com.agelousis.noteitdown.network.model.WikipediaBatchResponseModel
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface WikipediaApi {

    @POST(".")
    fun requestImageBy(
        @Query(value = "action") action: String = "query",
        @Query(value = "titles") titles: String,
        @Query(value = "prop") prop: String = "pageimages",
        @Query(value = "format") format: String = "json",
        @Query(value = "pithumbsize") pithumbsize: String = "100"
    ): Call<WikipediaBatchResponseModel?>

}