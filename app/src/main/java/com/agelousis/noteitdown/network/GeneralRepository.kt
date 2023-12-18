package com.agelousis.noteitdown.network

import com.agelousis.noteitdown.network.model.ErrorModel
import com.agelousis.noteitdown.utils.extensions.toModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias SuccessUnitBlock = () -> Unit
typealias SuccessBlock<T> = T.() -> Unit
typealias FailureBlock = (ErrorModel) -> Unit

typealias RequestInitializationBlock <I, R> = I.() -> Call<R>

object GeneralRepository {

    inline fun <reified I, R> request(
        requestInitializationBlock: RequestInitializationBlock<I, R?>,
        noinline successModelBlock: SuccessBlock<R?>,
        noinline failureBlock: FailureBlock
    ) {
        NetworkHelper.create<I>()
            ?.requestInitializationBlock()
            ?.enqueue(
                object: Callback<R?> {
                    override fun onResponse(call: Call<R?>, response: Response<R?>) {
                        if (response.code() == 200)
                            successModelBlock(response.body())
                        else
                            failureBlock(
                                response.errorBody()?.string()?.toModel<ErrorModel>()
                                    ?: return
                            )
                    }

                    override fun onFailure(call: Call<R?>, t: Throwable) {
                        failureBlock(
                            ErrorModel(
                                message = t.localizedMessage
                            )
                        )
                    }
                }
            )
    }

}