package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.UsersFirebase
import kotlinx.coroutines.launch

class ChangePassViewModel : ViewModel() {


    //Deklaracija varijablih za upload profila
    val currentPass = MutableLiveData<String>()
    val newPass = MutableLiveData<String>()
    val newPassAgain = MutableLiveData<String>()


    //Kraj deklaracije




    fun changePass() {

        viewModelScope.launch {
            UsersFirebase.changePass(
                currentPass.value.toString(),
                newPass.value.toString(),
                newPassAgain.value.toString()
            )
        }


    }



}