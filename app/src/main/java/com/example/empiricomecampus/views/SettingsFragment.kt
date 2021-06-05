@file:Suppress("DEPRECATION")

package com.example.empiricomecampus.views

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.empiricomecampus.databinding.FragmentSettingsBinding
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.utils.Constants.GALLERY_REQUEST_CODE
import com.example.empiricomecampus.utils.Globals.USER_ID
import com.example.empiricomecampus.utils.Globals.USER_NAME
import com.example.empiricomecampus.viewmodels.SettingsViewModel
import com.example.empiricomecampus.viewmodels.SettingsViewModelFactory
import com.google.firebase.storage.FirebaseStorage

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val storageRefrence = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = SettingsViewModelFactory()
        val settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.viewModel = settingsViewModel

        settingsViewModel.navigateToChangePass.observe(viewLifecycleOwner, {
            it?.let {

                val action = SettingsFragmentDirections.actionSettingsFragmentToChangePassFragment()
                findNavController().navigate(action)

            }
        })

        settingsViewModel.navigateToChangeImg.observe(viewLifecycleOwner, {
            it?.let {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Odaberite fotografiju"),
                    GALLERY_REQUEST_CODE
                )
            }
        })

        settingsViewModel.logOut.observe(viewLifecycleOwner, {
            it?.let {
                editor.clear()
                editor.apply()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        settingsViewModel.imgUri.observe(viewLifecycleOwner, {
            Glide.with(requireActivity()).load(it).into(binding.imgUser)
            Log.d("ImgRead", "Value of img $it")
        })

        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            binding.imgUser.setImageURI(imageUri)
            uploadToFirebase(imageUri!!)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uploadToFirebase(uri: Uri) {
        val fileRef = storageRefrence.child(USER_NAME.value.toString())
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener {
                Log.d("ImageUp", "Upload")
                UsersFirebase.databaseReference.child(USER_ID.value.toString())
                    .child("img").setValue(it.toString())

            }
        }.addOnProgressListener {
            Log.d("ImageUp", "Upload")
        }.addOnFailureListener {
            Log.d("ImageUp", it.toString())
        }
    }

}