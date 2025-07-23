package com.agelousis.noteitdown.network.model

data class WikipediaQueryModel(
    val normalized: List<WikipediaQueryNormalizedModel>?,
    val pages: Map<String, Any>?
)