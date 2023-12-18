package com.agelousis.noteitdown.network.model

data class WikipediaBatchResponseModel(
    val batchcomplete: String?,
    val query: WikipediaQueryModel?
)