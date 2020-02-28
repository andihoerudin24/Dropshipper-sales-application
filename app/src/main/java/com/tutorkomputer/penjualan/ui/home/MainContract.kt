package com.tutorkomputer.penjualan.ui.home

interface MainContract {


    interface View {
        fun initListener()
        fun showMessage(message: String)
    }
}