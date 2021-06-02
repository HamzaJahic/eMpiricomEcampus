package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.FragmentAlertDetailsBinding
import com.example.empiricomecampus.models.Alert
import com.example.empiricomecampus.utils.AlertDialogBuilders
import com.example.empiricomecampus.utils.Globals.Companion.ADMIN
import com.example.empiricomecampus.viewmodels.AlertDetailsViewModel
import com.example.empiricomecampus.viewmodels.AlertDetailsViewModelFactory
import com.example.empiricomecampus.viewmodels.MainActivityViewModel

class AlertsDetailsFragment : Fragment() {

    private var _binding: FragmentAlertDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertsDetailsViewModel: AlertDetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlertDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val alert = Alert()
        val viewModelFactory = AlertDetailsViewModelFactory(alert)

        if (ADMIN.value!!) {
            setHasOptionsMenu(true)
        }

        alertsDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AlertDetailsViewModel::class.java)

        binding.viewModel = alertsDetailsViewModel

        alertsDetailsViewModel.navigateToAlerts.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    AlertsDetailsFragmentDirections.actionAlertsDetailsFragmentToAlertsFragment()
                findNavController().navigate(action)
            }
        })

        alertsDetailsViewModel.showAlertDialog.observe(viewLifecycleOwner, {
            it?.let {
                val alertDialog = AlertDialogBuilders()
                alertDialog.createDeleteAlert(requireContext(),
                    { alertsDetailsViewModel.deleteEntry() })

            }

        })

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Options Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteEntry -> {
                alertsDetailsViewModel.showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}