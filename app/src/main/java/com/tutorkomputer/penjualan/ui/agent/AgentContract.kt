package com.tutorkomputer.penjualan.ui.agent

import com.tutorkomputer.penjualan.data.model.agent.DataAgent
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentList
import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentUpdate

interface AgentContract {


    interface Presenter{
        fun getAgent()
        fun deleteAgent(kd_agent:Long)
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoadingAgent(loading: Boolean)
        fun onResultAgent(responseAgentList: ResponseAgentList)
        fun onResultDelete(responseAgentUpdate: ResponseAgentUpdate)
        fun showMessage (message: String)
        fun showDialogDelete(dataAgent: DataAgent, position: Int)
        fun showDialogDeail(dataAgent: DataAgent, position: Int)
    }
}