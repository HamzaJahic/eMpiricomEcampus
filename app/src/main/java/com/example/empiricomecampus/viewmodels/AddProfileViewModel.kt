package com.example.empiricomecampus.viewmodels

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.models.Student
import com.example.empiricomecampus.utils.Constants.DEFAULT_PHOTO
import kotlinx.coroutines.launch

class AddProfileViewModel : ViewModel() {

    //Deklaracija varijablih za upload profila
    val name = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    private val _semester = MutableLiveData<String>()
    val semester: LiveData<String>
        get() = _semester

    private val _course = MutableLiveData<String>()
    val course: LiveData<String>
        get() = _course

    //Kraj deklaracije

    //Varijabla za navigaciju nazad na glavni fragment
    private val _navigateToProfile = MutableLiveData<Boolean?>()
    val navigateToProfile: LiveData<Boolean?>
        get() = _navigateToProfile

    //Spinner listeneri
    val courseListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _course.value = parent?.getItemAtPosition(position) as String
        }
    }

    val semesterListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _semester.value = parent?.getItemAtPosition(position) as String
        }
    }
    //Kraj listenera

    //upload podataka
    fun uploadData() {
        val user = UsersFirebase.databaseReference

        val entryID = user.push().key.toString()

        viewModelScope.launch {

            val studentEntry = Student(
                entryID,
                "${name.value}.${lastName.value}",
                "empirica2021",
                name.value,
                lastName.value,
                DEFAULT_PHOTO,
                _course.value,
                _semester.value,
            )
            UsersFirebase.uploadData(entryID, studentEntry)
        }

    }

    //funkcije za navigaciju
    fun navigateToProfile() {
        _navigateToProfile.value = true
        doneNavigating()
    }

    fun doneNavigating() {
        _navigateToProfile.value = false
    }
}