package com.tutorkomputer.penjualan.ui.cart.add

import com.tutorkomputer.penjualan.data.model.cart.ResponseCartUpdate

interface CartAddContract {


    interface Presenter {
        fun addCart(username: String, kdProduk: Long, jumlah: Long)
    }

    interface View {
        fun initActivity()
        fun onLoading(loading: Boolean)
        fun onResult(responseCartAdd: ResponseCartUpdate)
        fun showMessage(message: String)
    }


}

