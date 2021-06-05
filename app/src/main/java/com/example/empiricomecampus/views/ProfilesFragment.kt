package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empiricomecampus.adapters.UsersAdapter
import com.example.empiricomecampus.databinding.FragmentProfilesBinding
import com.example.empiricomecampus.viewmodels.ProfileViewModel
import com.example.empiricomecampus.viewmodels.ProfileViewModelFactory

class ProfilesFragment : Fragment() {
    private var _binding: FragmentProfilesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilesBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewModelFactory = ProfileViewModelFactory()
        val profilesViewModel =
            ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        val options = profilesViewModel.getOptions(this)
        val adapter = UsersAdapter(options, requireActivity(), UsersAdapter.OnClickListener {
            profilesViewModel.startNavigateToProfileDetails(it)
        })

        binding.viewModel = profilesViewModel
        binding.profilesList.adapter = adapter

        profilesViewModel.navigateToAddProfile.observe(viewLifecycleOwner, {
            it?.let {
                val action = ProfilesFragmentDirections.actionProfilesFragmentToAddProfileFragment()
                findNavController().navigate(action)
            }
        })

        profilesViewModel.navigateToProfileDetails.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    ProfilesFragmentDirections.actionProfilesFragmentToProfileDetailsFragment(it)
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