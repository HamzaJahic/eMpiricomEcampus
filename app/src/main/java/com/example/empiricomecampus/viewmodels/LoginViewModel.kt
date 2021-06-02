package com.example.empiricomecampus.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.models.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class LoginViewModel(context: Context) : ViewModel() {


    //Deklaracija varijablih za upload profila
    val userName = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    var idUser = String()

    private val sharedPreferences: SharedPreferences? = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    val editor = sharedPreferences?.edit()

    private val _navigateToSchedule = MutableLiveData<Boolean?>()
    val navigateToSchedule: LiveData<Boolean?> get() = _navigateToSchedule

    private val _adminLogin = MutableLiveData<Boolean?>()
    val adminLogin: LiveData<Boolean?> get() = _adminLogin

    private val _showError = MutableLiveData<Boolean?>()
    val showError: LiveData<Boolean?> get() = _showError


    fun loginUser() {
        val entry = Student("", userName.value, password.value, "", "", "", "", "")

        viewModelScope.launch {
            UsersFirebase.databaseReference.addListenerForSingleValueEvent(object :
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshot.let {
                        val users = it.children
                        users.forEach {
                            val username = it.child("username").value
                            val password = it.child("password").value
                            val id = it.child("id").value
                            if (entry.username == username && entry.password == password) {
                                idUser = id.toString()
                                if (entry.username == "Admin.Admin") editor!!.putBoolean(
                                    "admin",
                                    true
                                ).apply()
                                navigateToSchedule()
                                return
                            } else showError()

                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }


            }
            )
        }


    }

    //funkcije za navigaciju
    fun navigateToSchedule() {
        _navigateToSchedule.value = true
        editor?.apply {
            putString("id", idUser)
            putBoolean("token", true)
        }!!.apply()
        doneNavigating()
    }

    fun doneNavigating() {
        _navigateToSchedule.value = null
    }

    fun showError() {
        _showError.value = true
        endShowError()

    }

    fun endShowError() {
        _showError.value = null
    }
}