package com.example.empiricomecampus.viewmodels

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.R
import com.example.empiricomecampus.firebase.ScheduleFirebase
import com.example.empiricomecampus.models.Schedule
import kotlinx.coroutines.launch

enum class ScheduleSort(val value: String) {
    PONEDJELJAK("1"),
    UTORAK("2"),
    SRIJEDA("3"),
    CETVRTAK("4"),
    PETAK("5")
}


class AddScheduleViewModel : ViewModel() {


    //Deklaracija varijablih za upload rasporeda
    private var day = String()
    private var subject = String()
    private var attendance = String()
    private var startTime = String()
    private var endTime = String()
    private var sorting = String()

    private val _semester = MutableLiveData<String>()
    val semester: LiveData<String>
        get() = _semester

    private val _course = MutableLiveData<String?>()
    val course: LiveData<String?>
        get() = _course


    private val _type = MutableLiveData<String>()
    val type: LiveData<String?>
        get() = _type


    //Kraj deklaracije

    //Varijabla za navigaciju nazad na glavni fragment

    private val _navigateToSchedule = MutableLiveData<Boolean?>()
    val navigateToSchedule: LiveData<Boolean?>
        get() = _navigateToSchedule


    //init blok za deklaraciju default vrijednosti deklarisanih varijabli

    init {
        day = R.string.default_day.toString()
        _semester.value = R.string.default_semester.toString()
        _course.value = R.string.course_ini.toString()
        subject = R.string.default_subject.toString()
        startTime = R.string.default_startTime.toString()
        endTime = R.string.default_endTime.toString()
        _type.value = R.string.default_type.toString()
        attendance = R.string.default_attendence.toString()
        sorting = ScheduleSort.PONEDJELJAK.value
        _navigateToSchedule.value = null

    }

    //clickListeners za promjenu vrijednosti varijabli
    val dayListener = object : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            day = parent?.getItemAtPosition(position) as String
            when (position) {
                0 -> sorting = ScheduleSort.PONEDJELJAK.value
                1 -> sorting = ScheduleSort.UTORAK.value
                2 -> sorting = ScheduleSort.SRIJEDA.value
                3 -> sorting = ScheduleSort.CETVRTAK.value
                4 -> sorting = ScheduleSort.PETAK.value
            }
        }
    }


    val semesterListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _semester.value = parent?.getItemAtPosition(position) as String
        }
    }

    val subjectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            subject = parent?.getItemAtPosition(position) as String
        }
    }

    val startTimeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            startTime = parent?.getItemAtPosition(position) as String
        }
    }

    val endTimeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            endTime = parent?.getItemAtPosition(position) as String
        }
    }

    val typeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _type.value = parent?.getItemAtPosition(position) as String
        }
    }

    val attendanceListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            attendance = parent?.getItemAtPosition(position) as String
        }
    }

    val courseListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _course.value = parent?.getItemAtPosition(position) as String
        }
    }
    //Kraj deklaracije clickListenera

    fun uploadData() {
        //Kreiranje entry-a za upload na bazu podataka
        val databaseReference = ScheduleFirebase.databaseReference
        val entryID = databaseReference.push().key.toString()

        val entry = Schedule(
            entryID,
            day,
            _semester.value,
            _course.value,
            subject,
            startTime,
            endTime,
            _type.value,
            attendance,
            sorting
        )

        viewModelScope.launch {
            ScheduleFirebase.uploadData(entryID, entry)
        }


    }


    //funkcije za navigaciju
    fun navigateToSchedule() {
        _navigateToSchedule.value = true
        doneNavigating()
    }

    fun doneNavigating() {
        _navigateToSchedule.value = false
    }
}