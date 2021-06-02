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
import com.example.empiricomecampus.viewmodels.MainActivityViewModel
import com.example.empiricomecampus.viewmodels.SettingsViewModel
import com.example.empiricomecampus.viewmodels.SettingsViewModelFactory
import com.google.firebase.storage.FirebaseStorage

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    val GALLERY_REQUEST_CODE = 123
    private val storageRefrence = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = SettingsViewModelFactory()
        val postavkeViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.viewModelPostavke = postavkeViewModel



        postavkeViewModel.navigateToChangePass.observe(viewLifecycleOwner, {
            it?.let {

                val action = SettingsFragmentDirections.actionSettingsFragmentToChangePassFragment()
                findNavController().navigate(action)

            }
        })

        postavkeViewModel.navigateToChangeImg.observe(viewLifecycleOwner, {
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

        postavkeViewModel.logOut.observe(viewLifecycleOwner, {
            it?.let {
                editor.clear()
                editor.apply()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })

        postavkeViewModel.imgUri.observe(viewLifecycleOwner, {
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
        val fileRef = storageRefrence.child(MainActivityViewModel.name.value.toString())
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener {
                Log.d("ImageUp", "Upload")
                UsersFirebase.databaseReference.child(MainActivityViewModel.id.value.toString())
                    .child("img").setValue(it.toString())

            }
        }.addOnProgressListener {
            Log.d("ImageUp", "Upload")
        }.addOnFailureListener {
            Log.d("ImageUp", it.toString())
        }
    }

}