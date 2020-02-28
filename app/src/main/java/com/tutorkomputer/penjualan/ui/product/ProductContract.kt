package com.tutorkomputer.penjualan.ui.product

import com.tutorkomputer.penjualan.data.model.Product.ResponseCategoryList
import com.tutorkomputer.penjualan.data.model.Product.ResponseProductList

interface ProductContract {

    interface Presenter {
        fun getCategory()
        fun getProduct(kd_kategori: Long)
    }

    interface View {
        fun initActivity()
        fun onLoading(loading: Boolean)
        fun onResultCategory(responseCategoryList: ResponseCategoryList)
        fun onResultProduct(responseProductList: ResponseProductList)
    }

}