package com.tutorkomputer.penjualan.ui.agent.update

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
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentDetail
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate
import com.tutorkomputer.penjualan.ui.agent.AgentMapsActivity
import com.tutorkomputer.penjualan.utils.FileUtils
import com.tutorkomputer.penjualan.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_agent_create.*

class AgentUpdateActivity : AppCompatActivity(), AgentUpdateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1

    lateinit var presenter: AgentUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_create)
        presenter = AgentUpdatePresenter(this)
        presenter.getDetail(Constant.AGENT_ID)
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

    override fun initAvticity() {
        supportActionBar!!.title = "Agent Update"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        edtLocation.setOnClickListener {
            startActivity(Intent(this, AgentMapsActivity::class.java))
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
                location.isNullOrEmpty()) {
                showMessage( "Lengkapi data dengan benar" )
            } else {
                presenter.UpdateAgent(
                    Constant.AGENT_ID,nameStore.toString(), nameOwner.toString(), address.toString(), Constant.LATITUDE,
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
                btnSave.visibility= View.GONE
            }
            false ->{
                progress.visibility= View.GONE
                btnSave.visibility= View.VISIBLE
            }
        }
    }

    override fun onResultDetail(responseAgentDetail: ResponseAgentDetail) {
       val agent = responseAgentDetail.dataAgent

        edtNameStore.setText(agent.nama_toko)
        edtNameOwner.setText(agent.nama_pemilik)
        edtAddress.setText(agent.alamat)
        edtLocation.setText("${agent.latitude}, ${agent.longitude}")

        Constant.LATITUDE = agent.latitude!!
        Constant.LONGITUDE= agent.longitude!!

        GlideHelper.setImage(this,agent.gambar_toko!!,imvImage)

    }

    override fun onResultUpdate(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage(responseAgentUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
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
}
