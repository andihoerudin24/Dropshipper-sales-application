package com.tutorkomputer.penjualan.ui.agent.update

import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentDetail
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate
import com.tutorkomputer.penjualan.network.ApiService
import com.tutorkomputer.penjualan.ui.agent.create.AgentCreateContract
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AgentUpdatePresenter (val view: AgentUpdateContract.View) : AgentUpdateContract.Presenter{


    init {
        view.initAvticity()
        view.initListener()
    }

    override fun getDetail(kd_agent: Long) {
       view.onLoading(true)
        ApiService.endpoint.getAgentDetail(kd_agent).enqueue( object : Callback<ResponseAgentDetail>{

            override fun onFailure(call: Call<ResponseAgentDetail>, t: Throwable) {
                   view.onLoading(false)
            }

            override fun onResponse(
                call: Call<ResponseAgentDetail>,
                response: Response<ResponseAgentDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseAgentDetail : ResponseAgentDetail? = response.body()
                    view.onResultDetail( responseAgentDetail!! )
                }
            }

        })
    }

    override fun UpdateAgent(
        kd_agent: Long,
        nama_toko: String,
        nama_pemilik: String,
        alamat: String,
        latitude: String,
        longitude: String,
        gambar_toko: File
    ) {
        val requestBody : RequestBody
        val multipartBody : MultipartBody.Part

        if (gambar_toko != null){
              requestBody = RequestBody.create(MediaType.parse("image/*"), gambar_toko)
              multipartBody = MultipartBody.Part.createFormData("gambar_toko",
                gambar_toko.name, requestBody)
        }else{
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody = MultipartBody.Part.createFormData("gambar_toko",
               "", requestBody)
        }


        view.onLoading(true)
        ApiService.endpoint.updateAgent(kd_agent,nama_toko,nama_pemilik,alamat,latitude,longitude,
            multipartBody,"PUT").enqueue(object  : Callback<ResponseAgentUpdate> {
            override fun onFailure(call: Call<ResponseAgentUpdate>, t: Throwable) {

               view.onLoading(false)

            }

            override fun onResponse(
                call: Call<ResponseAgentUpdate>,
                response: Response<ResponseAgentUpdate>
            ) {
               view.onLoading(false)
                if (response.isSuccessful){
                    val  responseAgentUpdate : ResponseAgentUpdate? = response.body()
                    view.onResultUpdate(responseAgentUpdate!!)
                }
            }

        } )
    }

}