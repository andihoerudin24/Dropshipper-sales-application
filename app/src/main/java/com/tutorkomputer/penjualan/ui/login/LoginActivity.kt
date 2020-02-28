package com.tutorkomputer.penjualan.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tutorkomputer.penjualan.R
import com.tutorkomputer.penjualan.data.database.PrefsManager
import com.tutorkomputer.penjualan.data.model.login.ResponseLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)

    }


    override fun initActivity() {
        supportActionBar!!.title = "Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        Login.setOnClickListener {
            presenter.doLogin(edtUsername.text.toString(),edtPassword.text.toString());
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
       // Log.e("loginactivity","responselogin:${responseLogin.pegawai}")
        presenter.setPrefs(prefsManager,responseLogin.pegawai!!)
        finish()
    }


    override fun onLoading(loading : Boolean){
        when(loading){
            true ->{
                progress.visibility = View.VISIBLE
                Login.visibility = View.GONE
            }
            false ->{
                progress.visibility = View.GONE
                Login.visibility = View.VISIBLE
            }
        }
    }





    override fun showMessage(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show();
    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return super.onSupportNavigateUp()
    }
}
