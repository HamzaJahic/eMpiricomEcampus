package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.FragmentScheduleDetailsBinding
import com.example.empiricomecampus.utils.AlertDialogBuilders
import com.example.empiricomecampus.utils.Globals.Companion.ADMIN
import com.example.empiricomecampus.viewmodels.MainActivityViewModel
import com.example.empiricomecampus.viewmodels.ScheduleDetailsViewModel
import com.example.empiricomecampus.viewmodels.ScheduleDetailsViewModelFactory

class ScheduleDetailsFragment : Fragment() {

    private var _binding: FragmentScheduleDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var scheduleDetailsViewModel: ScheduleDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        val schedule = ScheduleDetailsFragmentArgs.fromBundle(requireArguments()).schcedule
        val viewModelFactory = ScheduleDetailsViewModelFactory(schedule)

        if (ADMIN.value!!) {
            setHasOptionsMenu(true)
        }

        scheduleDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ScheduleDetailsViewModel::class.java)
        binding.viewModel = scheduleDetailsViewModel

        scheduleDetailsViewModel.navigateToSchedule.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    ScheduleDetailsFragmentDirections.actionScheduleDetailsFragmentToScheduleFragment()
                findNavController().navigate(action)
            }
        })

        scheduleDetailsViewModel.showAlertDialog.observe(viewLifecycleOwner, {
            it?.let {
                val alertDialog = AlertDialogBuilders()
                alertDialog.createDeleteAlert(
                    requireContext()
                ) { scheduleDetailsViewModel.deleteEntry() }

            }

        })

        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteEntry -> {
                scheduleDetailsViewModel.showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}