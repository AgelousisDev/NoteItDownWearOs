package com.agelousis.noteitdown.noteItDown.viewModel

import androidx.lifecycle.ViewModel
import com.agelousis.noteitdown.network.GeneralRepository
import com.agelousis.noteitdown.network.SuccessBlock
import com.agelousis.noteitdown.network.apis.WikipediaApi
import com.agelousis.noteitdown.network.model.WikipediaBatchResponseModel
import com.agelousis.noteitdown.network.model.WikipediaQueryPageModel
import com.agelousis.noteitdown.utils.extensions.toModel
import org.json.JSONObject

class NoteItDownBaseViewModel: ViewModel() {

    fun requestProductImage(
        product: String,
        successBlock: SuccessBlock<String>
    ) {
        GeneralRepository.request<WikipediaApi, WikipediaBatchResponseModel>(
            requestInitializationBlock = WikipediaApi@ {
                this@WikipediaApi.requestImageBy(
                    titles = product
                )
            },
            successModelBlock = WikipediaBatchResponseModel@ {
                successBlock(
                    (this@NoteItDownBaseViewModel productImageUrl this@WikipediaBatchResponseModel)
                        ?: return@WikipediaBatchResponseModel
                )
            },
            failureBlock = {}
        )
    }

    private infix fun productImageUrl(
        wikipediaBatchResponseModel: WikipediaBatchResponseModel?
    ) = with(
        receiver = wikipediaBatchResponseModel?.query?.pages
    ) WikipediaQueryPageData@ {
        if (this@WikipediaQueryPageData.isNullOrEmpty())
            return@WikipediaQueryPageData null
        val jsonObject = JSONObject(
            this@WikipediaQueryPageData
        )
        with(
            receiver = jsonObject.keys().asSequence().toList().firstOrNull()?.toString()
                ?: return@WikipediaQueryPageData null
        ) Key@ {
            jsonObject.getJSONObject(this@Key).toString().toModel<WikipediaQueryPageModel>()?.thumbnail?.source
        }
    }

}