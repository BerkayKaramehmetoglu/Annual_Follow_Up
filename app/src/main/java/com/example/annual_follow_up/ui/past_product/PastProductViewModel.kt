package com.example.annual_follow_up.ui.past_product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO

class PastProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<FollowUp>>()
    val products: LiveData<List<FollowUp>> get() = _products

    private lateinit var followUpDAO: FollowUpDAO

    fun init(context: Context) {
        val dbHelper = DatabaseHelper(context)
        followUpDAO = FollowUpDAO()
        loadProducts(dbHelper)
    }

    private fun loadProducts(dbHelper: DatabaseHelper) {
        val productList = followUpDAO.allSelectProducts(dbHelper)
        _products.postValue(productList)
    }

    fun refreshProducts(context: Context) {
        val dbHelper = DatabaseHelper(context)
        loadProducts(dbHelper)
    }
}