package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.databinding.FragmentAddAlertBinding
import com.example.empiricomecampus.viewmodels.AddAlertViewModel
import com.example.empiricomecampus.viewmodels.AddAlertViewModelFactory

class AddAlertFragment : Fragment() {

    private var _binding: FragmentAddAlertBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlertBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewModelFactory = AddAlertViewModelFactory()
        val addAlertViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddAlertViewModel::class.java)

        binding.viewModel = addAlertViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addAlertViewModel.navigateToAlerts.observe(viewLifecycleOwner, {
            it?.let {
                val action = AddAlertFragmentDirections.actionAddAlertFragmentToAlertsFragment()
                findNavController().navigate(action)
            }
        })

        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}