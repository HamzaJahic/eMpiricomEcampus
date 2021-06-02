package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.FragmentProfileDetailsBinding
import com.example.empiricomecampus.utils.AlertDialogBuilders
import com.example.empiricomecampus.viewmodels.MainActivityViewModel
import com.example.empiricomecampus.viewmodels.ProfileDetailsViewModel
import com.example.empiricomecampus.viewmodels.ProfileDetailsViewModelFactory

class ProfileDetailsFragment : Fragment() {

    private var _binding: FragmentProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileDetailsViewModel: ProfileDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        val student = ProfileDetailsFragmentArgs.fromBundle(requireArguments()).profile
        val viewModelFactory = ProfileDetailsViewModelFactory(student)

        if (MainActivityViewModel._admin.value!!) {
            setHasOptionsMenu(true)
        }

        profileDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(
            ProfileDetailsViewModel::class.java
        )

        binding.viewModel = profileDetailsViewModel



        profileDetailsViewModel.showAlertDialog.observe(viewLifecycleOwner, {
            it?.let {
                val alertDialog = AlertDialogBuilders()
                alertDialog.createDeleteAlert(
                    requireContext()
                ) { profileDetailsViewModel.deleteEntry() }
            }

        })

        profileDetailsViewModel.navigateToProfiles.observe(viewLifecycleOwner, {
            it?.let {
                val action =
                    ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToProfilesFragment()
                findNavController().navigate(action)
            }
        })


        Glide.with(requireActivity()).load(student.img).into(binding.imgUser)

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
                profileDetailsViewModel.showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}