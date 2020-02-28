package com.tutorkomputer.penjualan.ui.transaction.detail

import android.util.Log
import com.tutorkomputer.penjualan.data.model.transaction.detail.ResponseTransactionDetail
import com.tutorkomputer.penjualan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailPresenter (val view:TransactionDetailContract.View) :
                                  TransactionDetailContract.Presenter {

    init {
        view.initFragment()
    }

    override fun getTransactionByInvoice(invoice: String) {
        view.onLoading(true)
        ApiService.endpoint.getTransactionByInvoice(invoice).enqueue(object : Callback<ResponseTransactionDetail>{
            override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
               view.onLoading(false)
            }

            override fun onResponse(call: Call<ResponseTransactionDetail>,
                                    response: Response<ResponseTransactionDetail> ) {
                Log.e("TransDetailPresenter", response.toString())
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseTransactionDetail: ResponseTransactionDetail? = response.body()
                    view.onResult(responseTransactionDetail!!)
                }
            }
        })
    }


}