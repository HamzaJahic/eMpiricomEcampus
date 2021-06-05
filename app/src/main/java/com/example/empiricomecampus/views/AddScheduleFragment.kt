package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.FragmentAddScheduleBinding
import com.example.empiricomecampus.viewmodels.AddScheduleViewModel
import com.example.empiricomecampus.viewmodels.AddScheduleViewModelFactory

class AddScheduleFragment : Fragment() {

    private var _binding: FragmentAddScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddScheduleBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewModelFactory = AddScheduleViewModelFactory()
        val addScheduleViewModel = ViewModelProvider(this, viewModelFactory).get(
            AddScheduleViewModel::class.java
        )

        binding.viewModel = addScheduleViewModel

        addScheduleViewModel.navigateToSchedule.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    AddScheduleFragmentDirections.actionAddScheduleFragmentToScheduleFragment()
                findNavController().navigate(action)
            }
        })

        addScheduleViewModel.semester.observe(viewLifecycleOwner, {
            it?.let {
                val subjects = changeSubjects(it, addScheduleViewModel.course.value)
                val adapter = ArrayAdapter.createFromResource(
                    requireActivity().applicationContext,
                    subjects,
                    android.R.layout.simple_spinner_item
                )
                    .also {
                        it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                binding.spinnerPredmet.adapter = adapter
            }
        })

        addScheduleViewModel.course.observe(viewLifecycleOwner, {
            it?.let {
                val subjects = changeSubjects(addScheduleViewModel.semester.value, it)
                val adapter = ArrayAdapter.createFromResource(
                    requireActivity().applicationContext,
                    subjects,
                    android.R.layout.simple_spinner_item
                )
                    .also {
                        it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                binding.spinnerPredmet.adapter = adapter
            }
        })

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeSubjects(semester: String?, course: String?): Int {
        val semesters = resources.getStringArray(R.array.semester)
        var subjects: Int
        subjects = R.array.subject_semestar_I_INI
        if (course == "INI") {
            when (semester) {
                semesters[0].toString() -> subjects = R.array.subject_semestar_I_INI
                semesters[1].toString() -> subjects = R.array.subject_semestar_II_INI
                semesters[2].toString() -> subjects = R.array.subject_semestar_III_INI
                semesters[3].toString() -> subjects = R.array.subject_semestar_IV_INI
                semesters[4].toString() -> subjects = R.array.subject_semestar_V_INI
                semesters[5].toString() -> subjects = R.array.subject_semestar_VI_INI
                semesters[6].toString() -> subjects = R.array.subject_semestar_VII_INI
                semesters[7].toString() -> subjects = R.array.subject_semestar_VIII_INI
            }
        } else {
            when (semester) {
                semesters[0].toString() -> subjects = R.array.subject_semestar_I_POI
                semesters[1].toString() -> subjects = R.array.subject_semestar_II_POI
                semesters[2].toString() -> subjects = R.array.subject_semestar_III_POI
                semesters[3].toString() -> subjects = R.array.subject_semestar_IV_POI
                semesters[4].toString() -> subjects = R.array.subject_semestar_V_POI
                semesters[5].toString() -> subjects = R.array.subject_semestar_VI_POI
                semesters[6].toString() -> subjects = R.array.subject_semestar_VII_POI
                semesters[7].toString() -> subjects = R.array.subject_semestar_VIII_POI
            }
        }
        return subjects
    }
}