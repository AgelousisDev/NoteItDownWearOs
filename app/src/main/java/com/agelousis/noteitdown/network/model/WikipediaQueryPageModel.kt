package com.agelousis.noteitdown.network.model


data class WikipediaQueryPageModel(
    val pageid: Int?,
    val ns: Int?,
    val title: String?,
    val pageimage: String?,
    val thumbnail: WikipediaQueryPageThumbnailModel?
)