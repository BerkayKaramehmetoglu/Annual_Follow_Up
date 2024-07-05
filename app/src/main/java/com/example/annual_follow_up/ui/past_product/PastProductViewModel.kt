package com.example.annual_follow_up.ui.past_product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO

class PastProductViewModel(application: Application) : AndroidViewModel(application) {

    private val followUpDAO = FollowUpDAO()
    private var _productList = MutableLiveData<List<FollowUp>>()
    private var _totalProduct = MutableLiveData<Int>()

    init {
        loadProducts()
        totalProducts()
    }

    val productsList: LiveData<List<FollowUp>>
        get() = _productList

    val totalProduct: LiveData<Int>
        get() = _totalProduct

    fun loadProducts() {
        val dbHelper = DatabaseHelper(getApplication())
        val productsFromDb = followUpDAO.allSelectProducts(dbHelper)
        _productList.value = productsFromDb
    }

    fun totalProducts() {
        val dbHelper = DatabaseHelper(getApplication())
        val productDb = followUpDAO.totalProduct(dbHelper)
        _totalProduct.value = productDb
    }


}