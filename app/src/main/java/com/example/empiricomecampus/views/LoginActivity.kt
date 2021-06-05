package com.example.empiricomecampus.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.empiricomecampus.databinding.ActivityLoginBinding
import com.example.empiricomecampus.viewmodels.LoginViewModel
import com.example.empiricomecampus.viewmodels.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        val viewModelFactory = LoginViewModelFactory(this)
        val loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        val contextView = binding.btnLogin

        setContentView(binding.root)
        binding.viewModel = loginViewModel

        loginViewModel.navigateToSchedule.observe(this, {
            it?.let {
                val intent = Intent(this, MainActivity::class.java)
                    .putExtra("id", loginViewModel.idUser)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                loginViewModel.doneNavigating()
            }
        })

        loginViewModel.showError.observe(this, {
            it?.let {
                hideKeyboard()
                Snackbar.make(
                    contextView,
                    "Pogrešan username ili password",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("OK") {
                        loginViewModel.endShowError()
                    }
                    .show()
                loginViewModel.endShowError()

            }
        })
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}