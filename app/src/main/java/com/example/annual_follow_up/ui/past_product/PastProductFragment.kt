package com.example.annual_follow_up.ui.past_product

import RVAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annual_follow_up.databinding.FragmentPastProductBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUpDAO

class PastProductFragment : Fragment() {

    private var _binding: FragmentPastProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PastProductViewModel
    private lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PastProductViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PastProductViewModel::class.java)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.productsList.observe(viewLifecycleOwner) {
            adapter = RVAdapter(requireContext(), it)
            binding.rv.adapter = adapter
        }

        viewModel.totalProduct.observe(viewLifecycleOwner) {

            val vt = DatabaseHelper(requireContext())

            binding.countProduct.text = FollowUpDAO().totalProduct(vt).toString()
            limitControl(FollowUpDAO.count)
        }

    }

    private fun limitControl(count: Int): Boolean {
        val vt = DatabaseHelper(requireContext())
        return when {
            count >= 10 -> {
                FollowUpDAO().deleteTable(vt, "FollowUp")
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts()
        viewModel.totalProducts()
    }
}