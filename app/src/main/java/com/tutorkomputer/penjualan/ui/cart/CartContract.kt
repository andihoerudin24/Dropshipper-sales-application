package com.tutorkomputer.penjualan.ui.cart

import com.tutorkomputer.penjualan.data.model.cart.ResponseCartList
import com.tutorkomputer.penjualan.data.model.cart.ResponseCartUpdate
import com.tutorkomputer.penjualan.data.model.cart.ResponseCheckout

interface CartContract {


    interface Presenter{
        fun getCart(username:String)

        fun deleteItemCart(kd_keranjang:Long)
        fun deleteCart(username: String)
        
        
        fun checkout(username: String,kd_agen:Long)
        
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoadingCart(loading: Boolean)
        fun onResultCart(responseCartList: ResponseCartList)
        fun showMessage(message: String)

        fun onResultDelete(responseCartUpdate: ResponseCartUpdate)
        fun showDisplay()


        fun onLoadingCheckout(loading: Boolean)
        fun onReesultCheckout(responseCheckout:ResponseCheckout)
    }
}
