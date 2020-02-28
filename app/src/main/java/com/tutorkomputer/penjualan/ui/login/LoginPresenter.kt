package com.tutorkomputer.penjualan.ui.login

import com.tutorkomputer.penjualan.data.database.PrefsManager
import com.tutorkomputer.penjualan.data.model.login.DataLogin
import com.tutorkomputer.penjualan.data.model.login.ResponseLogin
import com.tutorkomputer.penjualan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val view:LoginContract.View) : LoginContract.Presenter{


    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)

    }

    override fun doLogin(username: String, password: String) {
        view.onLoading(true);
        ApiService.endpoint.loginUser(username,password)
            .enqueue(object  : Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    view.onLoading(false);
                }

                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    view.onLoading(false);
                    if (response.isSuccessful){

                        val responseLogin: ResponseLogin? =response.body()
                        responseLogin!!.msg?.let { view.showMessage(it) }

                        if (responseLogin!!.status!!){
                               view.onResult(responseLogin)
                        }

                    }
                }

            })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin) {
       prefsManager.prefsisLogin = true
       prefsManager.prefsUsername= dataLogin.username!!
       prefsManager.prefsNamaPegawai=dataLogin.nama_pegawai!!
       prefsManager.prefsJK=dataLogin.jk!!
       prefsManager.prefsAlamat=dataLogin.alamat!!
       prefsManager.prefsisAktif=dataLogin.is_aktif!!

  }


}