package com.tutorkomputer.penjualan.ui.agent.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lazday.poslaravel.util.GalleryHelper
import com.tutorkomputer.penjualan.R
import com.tutorkomputer.penjualan.data.Constant
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate
import com.tutorkomputer.penjualan.ui.agent.AgentMapsActivity
import com.tutorkomputer.penjualan.utils.FileUtils
import kotlinx.android.synthetic.main.activity_agent_create.*
import java.net.URI
import kotlin.math.log

class AgentCreateActivity : AppCompatActivity(), AgentCreateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1

    lateinit var presenter: AgentCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_create)


        presenter = AgentCreatePresenter(this)

    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()){
            edtLocation.setText("${Constant.LATITUDE} , ${Constant.LONGITUDE}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE=""
        Constant.LONGITUDE=""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK){
            uriImage = data!!.data
            imvImage.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun initAvticity() {
        supportActionBar!!.title = "Agent Baru"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        edtLocation.setOnClickListener {
            startActivity(Intent(this,AgentMapsActivity::class.java))
        }

        imvImage.setOnClickListener {
            if (GalleryHelper.permissionGallery(this,this,pickImage)){
                GalleryHelper.openGallery(this)
            }
        }

        btnSave.setOnClickListener {

            val nameStore   = edtNameStore.text
            val nameOwner   = edtNameOwner.text
            val address     = edtAddress.text
            val location    = edtLocation.text


            if ( nameStore.isNullOrEmpty() || nameOwner.isNullOrEmpty() || address.isNullOrEmpty() ||
                location.isNullOrEmpty() || uriImage == null ) {
                showMessage( "Lengkapi data dengan benar" )
            } else {
                presenter.insertAgent(
                    nameStore.toString(), nameOwner.toString(), address.toString(), Constant.LATITUDE,
                    Constant.LONGITUDE, FileUtils.getFile(this, uriImage)
                )
                Log.e("gambar", FileUtils.getFile(this, uriImage).toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
             true -> {
                 progress.visibility = View.VISIBLE
                 btnSave.visibility=View.GONE
             }
            false ->{
                progress.visibility=View.GONE
                btnSave.visibility=View.VISIBLE
            }
        }
    }

    override fun onResult(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage(responseAgentUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
