package com.tutorkomputer.penjualan.ui.login

import com.tutorkomputer.penjualan.data.database.PrefsManager
import com.tutorkomputer.penjualan.data.model.login.DataLogin
import com.tutorkomputer.penjualan.data.model.login.ResponseLogin

interface LoginContract{


    interface Presenter{

        fun doLogin(username: String, password: String)
        fun setPrefs(prefsManager: PrefsManager,dataLogin: DataLogin)


    }


    interface  View{
        fun initActivity()
        fun initListener()
        fun onLoading(loading : Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message : String)
    }
}