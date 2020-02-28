package com.tutorkomputer.penjualan.ui.transaction

import com.tutorkomputer.penjualan.data.model.transaction.ResponseTransactionList
import com.tutorkomputer.penjualan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionPresenter (val view: TransactionContract.View) : TransactionContract.Presenter{

    init {
        view.initFragment()
    }

    override fun getTransactionByUsername(username: String) {
        view.onLoading(true);
        ApiService.endpoint.getTransactionByUsername(username).enqueue( object : Callback<ResponseTransactionList>{
            override fun onFailure(call: Call<ResponseTransactionList>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<ResponseTransactionList>,
                response: Response<ResponseTransactionList>
            ) {
                view.onLoading(false);
                if (response.isSuccessful){
                    val responseTransactionList:ResponseTransactionList? = response.body()
                    view.onResult(responseTransactionList!!)
                }
            }

        })
    }


}