package com.example.annual_follow_up.ui.past_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annual_follow_up.databinding.FragmentPastProductBinding

class PastProductFragment : Fragment() {

    private var _binding: FragmentPastProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PastProductViewModel
    private lateinit var adapter: RVAdapter


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

        adapter = RVAdapter(requireContext(), arrayListOf())
        binding.rv.adapter = adapter

        viewModel.allProducts.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateProducts(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}