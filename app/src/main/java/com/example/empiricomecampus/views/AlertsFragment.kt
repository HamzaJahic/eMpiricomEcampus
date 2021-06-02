package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.adapters.AlertAdapter
import com.example.empiricomecampus.databinding.FragmentAlertsBinding
import com.example.empiricomecampus.utils.Globals.Companion.ADMIN
import com.example.empiricomecampus.viewmodels.AlertsViewModel
import com.example.empiricomecampus.viewmodels.AlertsViewModelFactory
import com.example.empiricomecampus.viewmodels.MainActivityViewModel

class AlertsFragment : Fragment() {

    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        val view = binding.root

        if (!ADMIN.value!!) {
            binding.fab.visibility = View.GONE
        }


        val viewModelFactory = AlertsViewModelFactory(this)
        val alertsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AlertsViewModel::class.java)
        val options = alertsViewModel.options
        val adapter = AlertAdapter(options, AlertAdapter.OnClickListener {
            alertsViewModel.startNavigateToAlertDetails(it)
        })

        binding.viewModel = alertsViewModel
        binding.alertList.adapter = adapter


        alertsViewModel.navigateToAddAlert.observe(viewLifecycleOwner, {
            it?.let {
                val action = AlertsFragmentDirections.actionAlertsFragmentToAddAlertFragment()
                findNavController().navigate(action)

            }
        })

        alertsViewModel.navigateToAlertDetails.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    AlertsFragmentDirections.actionAlertsFragmentToAlertsDetailsFragment(it)
                findNavController().navigate(action)
            }
        })

        adapter.progressBar.observe(viewLifecycleOwner, {
            it?.let {
                binding.progressBar.visibility = View.GONE
            }
        })

        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}