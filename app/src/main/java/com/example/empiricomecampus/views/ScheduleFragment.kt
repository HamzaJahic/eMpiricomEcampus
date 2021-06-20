package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.R
import com.example.empiricomecampus.adapters.ScheduleAdapter
import com.example.empiricomecampus.databinding.FragmentScheduleBinding
import com.example.empiricomecampus.models.Schedule
import com.example.empiricomecampus.utils.Globals.ADMIN
import com.example.empiricomecampus.utils.Globals.USER_COURSE
import com.example.empiricomecampus.utils.Globals.USER_SEMESTER
import com.example.empiricomecampus.viewmodels.ScheduleViewModel
import com.example.empiricomecampus.viewmodels.ScheduleViewModelFactory
import com.firebase.ui.database.FirebaseRecyclerOptions

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var adapter: ScheduleAdapter
    private lateinit var options: FirebaseRecyclerOptions<Schedule>
    var semester = USER_SEMESTER.value.toString()
    var course = USER_COURSE.value.toString()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModelFactory = ScheduleViewModelFactory(this)
        scheduleViewModel =
            ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)

        if (!ADMIN.value!!) {
            binding.fab.visibility = View.GONE
        }
//
//        if (ADMIN.value!!) {
//            setHasOptionsMenu(true)
//        }

        binding.viewModel = scheduleViewModel

        options = scheduleViewModel.setOptions(course, semester)
        adapter = ScheduleAdapter(options, requireContext(), ScheduleAdapter.OnClickListener {
            scheduleViewModel.startNavigateToScheduleDetails(it)
        })

        scheduleViewModel.navigateToAddSchedule.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    ScheduleFragmentDirections.actionScheduleFragmentToAddScheduleFragment()
                findNavController().navigate(action)
                scheduleViewModel.doneNavigating()
            }
        })

        scheduleViewModel.navigateToScheduleDetails.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    ScheduleFragmentDirections.actionScheduleFragmentToScheduleDetailsFragment(it)
                findNavController().navigate(action)
                scheduleViewModel.doneNavigateToScheduleDetails()
            }
        })

        adapter.progressBar.observe(viewLifecycleOwner, {
            it?.let {
                binding.progressBar.visibility = View.GONE
            }
        })

        USER_SEMESTER.observe(viewLifecycleOwner, {
            semester = it
            val options = scheduleViewModel.setOptions(course, it)
            adapter = ScheduleAdapter(options, requireContext(), ScheduleAdapter.OnClickListener {
                scheduleViewModel.startNavigateToScheduleDetails(it)
            })
            binding.scheduleList.adapter = adapter
        })

        USER_COURSE.observe(viewLifecycleOwner, {
            course = it
            val options = scheduleViewModel.setOptions(it, semester)
            adapter = ScheduleAdapter(options, requireContext(), ScheduleAdapter.OnClickListener {
                scheduleViewModel.startNavigateToScheduleDetails(it)
            })
            binding.scheduleList.adapter = adapter
        })

        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iSemester -> {
                filterResults("I")
                true
            }
            R.id.iiSemester -> {
                filterResults("II")
                true
            }
            R.id.iiiSemester -> {
                filterResults("III")
                true
            }
            R.id.ivSemester -> {
                filterResults("IV")
                true
            }
            R.id.vSemester -> {
                filterResults("V")
                true
            }
            R.id.viSemester -> {
                filterResults("VI")
                true
            }
            R.id.viiSemester -> {
                filterResults("VII")
                true
            }
            R.id.viiiSemester -> {
                filterResults("VIII")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterResults(semester: String?) {
        options = scheduleViewModel.setOptions(course, semester)
        adapter = ScheduleAdapter(options, requireContext(), ScheduleAdapter.OnClickListener {
            scheduleViewModel.startNavigateToScheduleDetails(it)
        })
        binding.scheduleList.adapter = adapter
        binding.scheduleList.invalidate()
    }
}