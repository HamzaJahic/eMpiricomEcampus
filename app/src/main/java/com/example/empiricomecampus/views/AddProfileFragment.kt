package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.databinding.FragmentAddProfileBinding
import com.example.empiricomecampus.viewmodels.AddProfileViewModel
import com.example.empiricomecampus.viewmodels.AddProfileViewModelFactory

class AddProfileFragment : Fragment() {

    private var _binding: FragmentAddProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProfileBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewModelFactory = AddProfileViewModelFactory()
        val addProfileViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddProfileViewModel::class.java)

        binding.viewModel = addProfileViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addProfileViewModel.navigateToProfile.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    AddProfileFragmentDirections.actionAddProfileFragmentToProfilesFragment()
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