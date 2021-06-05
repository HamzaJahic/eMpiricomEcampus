package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.empiricomecampus.databinding.FragmentChangePassBinding
import com.example.empiricomecampus.viewmodels.ChangePassViewModel
import com.example.empiricomecampus.viewmodels.ChangePassViewModelFactory

class ChangePassFragment : Fragment() {
    private var _binding: FragmentChangePassBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePassBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewModelFactory = ChangePassViewModelFactory()
        val changePassViewModel =
            ViewModelProvider(this, viewModelFactory).get(ChangePassViewModel::class.java)

        binding.viewModel = changePassViewModel

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}