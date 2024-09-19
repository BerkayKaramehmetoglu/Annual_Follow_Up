package com.example.annual_follow_up.product_pages.past_product

import RVAdapterPastProduct
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annual_follow_up.databinding.FragmentPastProductBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.example.annual_follow_up.utils.Utils

class PastProductFragment : Fragment() {

    private var _binding: FragmentPastProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PastProductViewModel
    private lateinit var adapter: RVAdapterPastProduct

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vt = DatabaseHelper(requireContext())
        val limiter = FollowUpDAO().totalProduct(vt).toString().toInt().minus(5)

        val factory = PastProductViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[PastProductViewModel::class.java]

        _binding = FragmentPastProductBinding.inflate(inflater, container, false)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.pastProductLoad()

        viewModel.pastProduct.observe(viewLifecycleOwner) {
            adapter = RVAdapterPastProduct(requireContext(), it)
            binding.rv.adapter = adapter
        }

        viewModel.totalProduct.observe(viewLifecycleOwner) {
            binding.productCount.text = FollowUpDAO().totalProduct(vt).toString()
        }

        binding.nextImage.setOnClickListener {
            if (limiter <= Utils.offsetValues) {
                Utils.snackBar(it, "You are on the last page")
            } else {
                Utils.offsetValues += 5
                viewModel.pastProductLoad()
            }
        }

        binding.backImage.setOnClickListener {
            if (Utils.offsetValues <= 0) {
                Utils.snackBar(it, "You are on the first page")
            } else {
                Utils.offsetValues -= 5
                viewModel.pastProductLoad()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}