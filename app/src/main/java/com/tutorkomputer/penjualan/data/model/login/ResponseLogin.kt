package com.tutorkomputer.penjualan.data.model.login

import com.google.gson.annotations.SerializedName
import com.tutorkomputer.penjualan.data.model.login.DataLogin

data class ResponseLogin(

    @SerializedName("status") val status : Boolean?,
    @SerializedName("msg") val msg : String?,
    @SerializedName("pegawai") val pegawai : DataLogin?

 )