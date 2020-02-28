package com.tutorkomputer.penjualan.ui.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tutorkomputer.penjualan.R

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initActivity()
    }

    fun initActivity () {
        supportFragmentManager.beginTransaction()
            .add(R.id.container,TransactionFragment(),"transFrag")
            .commit()
    }

    override fun onNavigateUp(): Boolean {
        if (supportFragmentManager.findFragmentByTag("transFrag") == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,TransactionFragment(),"transFrag")
                .commit()
        }else{
            finish()
        }
        return super.onSupportNavigateUp()
    }
}
