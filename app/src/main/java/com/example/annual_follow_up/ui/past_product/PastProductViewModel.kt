package com.example.annual_follow_up.ui.past_product

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO

class PastProductViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(application)
    private val followUpDao: FollowUpDAO = FollowUpDAO()

    val allProducts: LiveData<List<FollowUp>> get() = followUpDao.allProducts

    init {
        refreshProducts()
    }

    fun refreshProducts() {
        val products = followUpDao.allSelectProducts(dbHelper)
        (followUpDao.allProducts as MutableLiveData).postValue(products)
    }
}