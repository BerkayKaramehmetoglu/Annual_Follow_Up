package com.example.annual_follow_up.ui.entry_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.annual_follow_up.databinding.FragmentEntryProductBinding

class EntryProductFragment : Fragment() {

    private var _binding: FragmentEntryProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val entryProductViewModel =
            ViewModelProvider(this).get(EntryProductViewModel::class.java)

        _binding = FragmentEntryProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.productName
        entryProductViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}