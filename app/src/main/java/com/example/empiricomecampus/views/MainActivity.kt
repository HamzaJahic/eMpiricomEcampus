package com.example.empiricomecampus.views

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.ActivityMainBinding
import com.example.empiricomecampus.utils.Constants.TOPIC
import com.example.empiricomecampus.utils.Globals.USER_COURSE
import com.example.empiricomecampus.utils.Globals.USER_LASTNAME
import com.example.empiricomecampus.utils.Globals.USER_NAME
import com.example.empiricomecampus.utils.Globals.USER_SEMESTER
import com.example.empiricomecampus.viewmodels.MainActivityViewModel
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_hostFragment) as NavHostFragment
        val textView = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.txtUserName)
        mainActivityViewModel = MainActivityViewModel(this)

        // app bar
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.scheduleFragment,
                R.id.alertsFragment,
                R.id.settingsFragment,
                R.id.empiricaWebsite,
                R.id.profilesFragment
            ),
            binding.drawerLayout
        )


        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        // app bar kraj

        binding.viewModel = mainActivityViewModel


        USER_NAME.observe(this, {
            textView.text = getString(R.string.nav_draw_username, it, textView.text)
        })

        USER_LASTNAME.observe(this, {
            textView.text = getString(R.string.nav_draw_username, textView.text, it)
        })

        USER_SEMESTER.observe(this, {
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.txtSemester).text =
                getString(R.string.nav_draw_semester, it)
        })

        mainActivityViewModel.imgUri.observe(this, {
            val imgView =
                binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.imgUser)
            Glide.with(this).load(it).into(imgView)
        })

        USER_COURSE.observe(this, {
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.txtCourse).text = it
        })


        if (!mainActivityViewModel.admin) {
            binding.navView.menu.removeItem(R.id.profilesFragment)
        }

        binding.navView.setupWithNavController(navController)

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.removeItem(3)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}