package com.example.annual_follow_up.ui.entry_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.annual_follow_up.databinding.FragmentEntryProductBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.google.android.material.snackbar.Snackbar

class EntryProductFragment : Fragment() {

    private var _binding: FragmentEntryProductBinding? = null
    private var _vt: DatabaseHelper? = null
    private var _followUpDao: FollowUpDAO? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryProductBinding.inflate(inflater, container, false)
        val root = binding.root

        registration()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registration() {
        _vt = context?.let { DatabaseHelper(it) }
        _followUpDao = FollowUpDAO()

        binding.submit.setOnClickListener {

            if (!checkLayoutEmpty(it)) return@setOnClickListener

            if (!checkLayoutLength(it)) return@setOnClickListener

            val productName = binding.productName.text.toString()
            val productAmount = binding.productAmount.text.toString().toInt()
            val productSales = binding.productSales.text.toString().toInt()
            val productExpense = binding.productExpense.text.toString().toInt()

            _vt?.let { vt ->
                _followUpDao?.insertProducts(
                    vt,
                    productName,
                    productAmount,
                    productSales,
                    productExpense
                )
            }

            snackBar(it, "Registration completed")
        }
    }


    private fun checkLayoutEmpty(view: View): Boolean {
        return when {
            binding.productName.text.isNullOrBlank() -> {
                snackBar(view, "Product name cannot be empty")
                false
            }

            binding.productAmount.text.isNullOrBlank() -> {
                snackBar(view, "Product amount cannot be empty")
                false
            }

            binding.productSales.text.isNullOrBlank() -> {
                snackBar(view, "Product sales cannot be empty")
                false
            }

            binding.productExpense.text.isNullOrBlank() -> {
                snackBar(view, "Product expense cannot be empty")
                false
            }

            else -> true
        }
    }

    private fun checkLayoutLength(view: View): Boolean {
        return when {

            binding.productName.text!!.length > 20 -> {
                snackBar(view, "The length of the product name cannot be longer than 20")
                false
            }

            binding.productAmount.text!!.length > 3 -> {
                snackBar(view, "Product ton quantity cannot exceed 3 digits")
                false
            }

            binding.productSales.text!!.length > 9 -> {
                snackBar(view, "Product sales amount cannot exceed million")
                false
            }

            binding.productExpense.text!!.length > 9 -> {
                snackBar(view, "Product expense amount cannot exceed million")
                false
            }

            else -> true
        }
    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

}