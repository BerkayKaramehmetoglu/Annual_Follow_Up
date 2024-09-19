package com.example.annual_follow_up.product_pages.entry_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.annual_follow_up.databinding.FragmentEntryProductBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.example.annual_follow_up.utils.Utils

class EntryProductFragment : Fragment() {

    private var _binding: FragmentEntryProductBinding? = null
    private var _vt: DatabaseHelper? = null
    private var _followUpDao: FollowUpDAO? = null
    private val binding get() = _binding!!
    private var typeValues = true

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
        var productType: String?
        _vt = context?.let { DatabaseHelper(it) }
        _followUpDao = FollowUpDAO()

        binding.productKg.setOnClickListener { typeValues = false }

        binding.submit.setOnClickListener {

            if (!checkLayoutEmpty(it)) return@setOnClickListener

            if (!checkLayoutLength(it)) return@setOnClickListener

            val productName = binding.productName.text.toString()
            var productAmount = binding.productAmount.text.toString().toDouble()
            val productSales = binding.productSales.text.toString().toInt()
            val productExpense = binding.productExpense.text.toString().toInt()
            val productDesc = binding.productDesc.text.toString()

            if (!typeValues && binding.productAmount.text.toString().toInt() > 999) {
                productAmount /= 1000
                typeValues = true
            }

            productType = if (typeValues) {
                binding.productTone.text.toString()
            } else {
                binding.productKg.text.toString()
            }

            _vt?.let { vt ->
                _followUpDao?.insertProducts(
                    vt,
                    productName,
                    productAmount,
                    productType!!,
                    productSales,
                    productExpense,
                    productDesc
                )
            }

            Utils.snackBar(it, "Registration completed")

            clearLayout()
        }
    }

    private fun checkLayoutEmpty(view: View): Boolean {
        return when {
            binding.productName.text.isNullOrBlank() -> {
                Utils.snackBar(view, "Product name cannot be empty")
                false
            }

            binding.productAmount.text.isNullOrBlank() -> {
                Utils.snackBar(view, "Product amount cannot be empty")
                false
            }

            binding.productAmount.text.startsWith("0") -> {
                Utils.snackBar(view, "Invalid product quantity")
                false
            }

            binding.productSales.text.isNullOrBlank() -> {
                Utils.snackBar(view, "Product sales cannot be empty")
                false
            }

            binding.productExpense.text.isNullOrBlank() -> {
                Utils.snackBar(view, "Product expense cannot be empty")
                false
            }

            binding.productDesc.text.isNullOrBlank() -> {
                Utils.snackBar(view, "Product description cannot be empty")
                false
            }

            else -> true
        }
    }

    private fun checkLayoutLength(view: View): Boolean {
        return when {

            binding.productName.text!!.length > 20 -> {
                Utils.snackBar(view, "The length of the product name cannot be longer than 20")
                false
            }

            binding.productDesc.text!!.length > 400 -> {
                Utils.snackBar(
                    view,
                    "The length of the product description cannot be longer than 400"
                )
                false
            }

            binding.productTone.isChecked && binding.productAmount.text!!.length > 3 -> {
                Utils.snackBar(view, "Invalid quantity entry for tons")
                false
            }

            else -> true
        }
    }

    private fun clearLayout() {
        binding.productName.text?.clear()
        binding.productAmount.text?.clear()
        binding.productSales.text?.clear()
        binding.productExpense.text?.clear()
        binding.productDesc.text?.clear()
        binding.entryProduct.clearFocus()
    }

}