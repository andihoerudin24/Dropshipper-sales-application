package com.tutorkomputer.penjualan.data.model.Product

import com.google.gson.annotations.SerializedName

data class ResponseCategoryList (

    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val dataCategory: List<DataCategory>
)