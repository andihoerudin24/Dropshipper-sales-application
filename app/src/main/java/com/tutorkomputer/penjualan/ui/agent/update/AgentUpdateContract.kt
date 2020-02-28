package com.tutorkomputer.penjualan.ui.agent.update

import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentDetail
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate
import java.io.File

interface AgentUpdateContract {


    interface Presenter{
        fun getDetail(kd_agent : Long)

        fun UpdateAgent(kd_agent: Long,nama_toko: String, nama_pemilik: String, alamat: String, latitude: String,
                        longitude: String, gambar_toko: File
        )

   }

    interface View{
        fun initAvticity()
        fun initListener()
        fun onLoading(loading:Boolean)
        fun onResultDetail(responseAgentDetail:ResponseAgentDetail)
        fun onResultUpdate(responseAgentUpdate: ResponseAgentUpdate)
        fun showMessage(message:String)
    }




}