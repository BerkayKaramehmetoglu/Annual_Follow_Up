package com.example.annual_follow_up.product_pages.past_product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PastProductViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PastProductViewModel::class.java)) {
            return PastProductViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}