package com.example.annual_follow_up.ui.entry_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EntryProductViewModel : ViewModel() {

    private val wiretappedData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _text = wiretappedData.apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text //getter
}