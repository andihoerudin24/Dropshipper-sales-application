package com.tutorkomputer.penjualan.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorkomputer.penjualan.R
import com.tutorkomputer.penjualan.data.model.Product.DataCategory
import com.tutorkomputer.penjualan.data.model.Product.DataProduct
import com.tutorkomputer.penjualan.data.model.Product.ResponseCategoryList
import com.tutorkomputer.penjualan.data.model.Product.ResponseProductList
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity(), ProductContract.View {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var presenter: PoductPresenter
    private var kdKategori: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar!!.hide()
        productAdapter = ProductAdapter(this, arrayListOf())
        categoryAdapter = CategoryAdapter(this, arrayListOf()) {
                category: DataCategory, position: Int ->
            kdKategori = category.kd_kategori!!
            presenter.getProduct( category.kd_kategori!! )
        }
        presenter = PoductPresenter(this)
        presenter.getCategory()
    }

    override fun initActivity() {
        rcvCategory.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }

        rcvProduct.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }

        swipe.setOnRefreshListener {
            when (View.VISIBLE) {
                rcvCategory.visibility -> presenter.getCategory()
                rcvProduct.visibility -> presenter.getProduct( kdKategori )
            }
        }

        imvClose.setOnClickListener {
            when (View.VISIBLE) {
                rcvCategory.visibility -> finish()
                rcvProduct.visibility -> {
                    rcvProduct.visibility = View.GONE
                    rcvCategory.visibility = View.VISIBLE
                    txvCategory.text = "Pilih Kategori"
                }
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                swipe.isRefreshing = true
                rcvCategory.visibility = View.GONE
                rcvProduct.visibility = View.GONE
            }
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultCategory(responseCategoryList: ResponseCategoryList) {
        rcvCategory.visibility = View.VISIBLE
        val dataCategory: List<DataCategory> = responseCategoryList.dataCategory
        categoryAdapter.updateCategory(dataCategory)
        txvCategory.text ="Pilih Kategori"
    }

    override fun onResultProduct(responseProductList: ResponseProductList) {
        rcvProduct.visibility = View.VISIBLE
        val dataProduct: List<DataProduct> = responseProductList.dataProduct
        productAdapter.updateProduct(dataProduct)
        txvCategory.text = dataProduct[0].kategori
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (View.VISIBLE) {
            rcvCategory.visibility -> finish()
            rcvProduct.visibility -> {
                rcvProduct.visibility = View.GONE
                rcvCategory.visibility = View.VISIBLE
                txvCategory.text = "Pilih Kategori"
            }
        }
    }
}
