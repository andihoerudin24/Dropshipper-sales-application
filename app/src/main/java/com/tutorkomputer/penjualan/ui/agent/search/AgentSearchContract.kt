package com.tutorkomputer.penjualan.ui.agent.search

import com.tutorkomputer.penjualan.data.model.agent.ResponseAgentList

interface AgentSearchContract {

    interface Presenter {
        fun getAgent()
        fun searchAgent(keyword: String)
    }

    interface View {
        fun initActivity()
        fun onLoadingAgent(loading: Boolean)
        fun onResultAgent(responseAgentList: ResponseAgentList)
    }
}