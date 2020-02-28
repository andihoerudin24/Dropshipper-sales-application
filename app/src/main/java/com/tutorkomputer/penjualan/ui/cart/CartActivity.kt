package com.tutorkomputer.penjualan.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorkomputer.penjualan.R
import com.tutorkomputer.penjualan.data.Constant
import com.tutorkomputer.penjualan.data.database.PrefsManager
import com.tutorkomputer.penjualan.data.model.cart.DataCart
import com.tutorkomputer.penjualan.data.model.cart.ResponseCartList
import com.tutorkomputer.penjualan.data.model.cart.ResponseCartUpdate
import com.tutorkomputer.penjualan.data.model.cart.ResponseCheckout
import com.tutorkomputer.penjualan.ui.agent.search.AgentSearchActivity
import com.tutorkomputer.penjualan.ui.cart.add.CartAddActivity
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(),CartContract.View {


    private lateinit var prefsManager: PrefsManager
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartPresenter: CartPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        prefsManager = PrefsManager(this)
        cartPresenter  = CartPresenter(this)
        cartPresenter.getCart(prefsManager.prefsUsername)

    }

    override fun initActivity() {
        supportActionBar!!.title = "Keranjang"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        cartAdapter = CartAdapter(this, arrayListOf()){
                dataCart: DataCart, position: Int ->
            cartPresenter.deleteItemCart(dataCart.kd_keranjang)

        }
    }

    override fun onStart() {
        super.onStart()
        if (Constant.IS_CHANGED){
            Constant.IS_CHANGED=false
            cartPresenter.getCart(prefsManager.prefsUsername)
            edtAgent.setText(Constant.AGENT_NAME)

        }
    }

    override fun initListener() {
        txvReset.visibility = View.GONE
        edtAgent.visibility = View.GONE

        rcvCart.apply { layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

       swipe.setOnRefreshListener {
           cartPresenter.getCart(prefsManager.prefsUsername)
       }

       btnAdd.setOnClickListener {
           startActivity(Intent(this,CartAddActivity::class.java))
       }

       txvReset.setOnClickListener {
            showDisplay()
       }

       edtAgent.setOnClickListener {
           startActivity(Intent(this,AgentSearchActivity::class.java))

       }

       btnCheckout.setOnClickListener {
            if (cartAdapter.cart.isNullOrEmpty()){
                showMessage("Keranjang Kosong")
            }else{
                if (edtAgent.text.isNullOrEmpty()){
                    edtAgent.error="Tidak Boleh Kosong"
                }else{
                    cartPresenter.checkout(prefsManager.prefsUsername,Constant.AGENT_ID)
                }
            }
       }
    }




    override fun onLoadingCart(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultCart(responseCartList: ResponseCartList) {
       val dataCart : List<DataCart> = responseCartList.dataCart
        if (dataCart.isNullOrEmpty()){
            txvReset.visibility = View.GONE
            edtAgent.visibility = View.GONE
        }else{
            cartAdapter.setData(dataCart)
            txvReset.visibility = View.VISIBLE
            edtAgent.visibility = View.VISIBLE
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun onResultDelete(responseCartUpdate: ResponseCartUpdate) {
         cartPresenter.getCart(prefsManager.prefsUsername)
         cartAdapter.removeAll()
    }

    override fun showDisplay() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus semua item dalam Keranjang?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            cartPresenter.deleteCart( prefsManager.prefsUsername )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onLoadingCheckout(loading: Boolean) {
        when(loading){
            true ->{
                btnCheckout.isEnabled = false
                btnCheckout.setText("Memuat ...")

            }
            false ->{
                btnCheckout.isEnabled = true
                btnCheckout.setText("Checkout")
            }
        }
    }

    override fun onReesultCheckout(responseCheckout: ResponseCheckout) {
       cartPresenter.getCart(prefsManager.prefsUsername)
        cartAdapter.removeAll()
    }


    override fun onDestroy() {
        super.onDestroy()
        Constant.CART_ID = 0
        Constant.AGENT_NAME = ""
    }
}
