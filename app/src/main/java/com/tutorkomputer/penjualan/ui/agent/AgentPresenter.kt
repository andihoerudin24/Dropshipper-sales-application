package com.tutorkomputer.penjualan.ui.agent

import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentList
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate
import com.tutorkomputer.penjualan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentPresenter (var  view:AgentContract.View) : AgentContract.Presenter{

   init {
       view.initActivity()
       view.initListener()
   }


    override fun getAgent() {
       view.onLoadingAgent(true)
        ApiService.endpoint.getAgent().enqueue(object : Callback<ResponseAgentList>{

            override fun onFailure(call: Call<ResponseAgentList>, t: Throwable) {
               view.onLoadingAgent(false)
            }

            override fun onResponse(
                call: Call<ResponseAgentList>,
                response: Response<ResponseAgentList>
            ) {
               view.onLoadingAgent(false)
                if (response.isSuccessful){
                    val responseAgentList: ResponseAgentList? = response.body()
                    view.onResultAgent(responseAgentList!!)
                }
            }

        })
    }

    override fun deleteAgent(kd_agent: Long) {
        view.onLoadingAgent(true)
        ApiService.endpoint.deleteAgent(kd_agent).enqueue(object : Callback<ResponseAgentUpdate> {
            override fun onFailure(call: Call<ResponseAgentUpdate>, t: Throwable) {
                view.onLoadingAgent(false)
            }

            override fun onResponse(
                call: Call<ResponseAgentUpdate>,
                response: Response<ResponseAgentUpdate>
            ) {
               view.onLoadingAgent(false)
                if (response.isSuccessful){
                    val responseAgentUpdate: ResponseAgentUpdate? = response.body()
                    view.onResultDelete(responseAgentUpdate!!)
                }
            }

        } )
    }

}