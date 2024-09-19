package com.example.annual_follow_up.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    var offsetValues = 0

    fun snackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

}