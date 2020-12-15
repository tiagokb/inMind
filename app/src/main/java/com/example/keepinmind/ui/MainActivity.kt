package com.example.keepinmind.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.keepinmind.R
import com.example.keepinmind.viewmodel.MainActivityViewModel
import com.example.keepinmind.viewmodelfactory.MainActivityViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var toolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View

    private lateinit var viewModel: MainActivityViewModel

    //header views
    private lateinit var headerImageProfile: ImageView
    private lateinit var headerUsername: TextView
    private lateinit var headerEmailAccount: TextView
    private lateinit var headerEditProfileBtn: ImageView

    private lateinit var navigationController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        val INTENT_ID_KEY = "id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        viewModel = ViewModelProvider(this, MainActivityViewModelFactory(application))
            .get(MainActivityViewModel::class.java)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        fab = findViewById(R.id.fab)

        drawerLayout = findViewById(R.id.drawer_layout)
        //nav view
        navigationView = findViewById(R.id.nav_view)

        //Eventos de click para o header e implementação dele com o perfil
        headerView = navigationView.getHeaderView(0)
        headerImageProfile = headerView.findViewById(R.id.nav_header_person_image)
        headerUsername = headerView.findViewById(R.id.nav_header_person_name)
        headerEmailAccount = headerView.findViewById(R.id.nav_header_person_account)
        headerEditProfileBtn = headerView.findViewById(R.id.nav_header_edit)


        viewModel.updateProfileHeader()

        //nav controller
        navigationController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notes, R.id.nav_post, R.id.nav_feed
            ), drawerLayout
        )
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        navigationView.setupWithNavController(navigationController)

        observe()
        fab.setOnClickListener(this)
        headerEditProfileBtn.setOnClickListener(this)
    }

    private fun observe() {
        viewModel.profileHeader.observe(this, Observer {
            if (it.userName != "") {
                headerUsername.text = it.userName
            } else {
                headerUsername.text = getString(R.string.inMider)
            }

            if (it.userEmail != "") {
                headerEmailAccount.text = it.userEmail
            } else {
                headerEmailAccount.text = getString(R.string.user_not_registered)
            }

            if (it.bitmap != null) {
                headerImageProfile.setImageBitmap(it.bitmap)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(v: View) {

        val actualFragment = navigationController.currentBackStackEntry?.destination?.label

        when (v.id) {
            R.id.fab -> when (actualFragment) {
                "Rascunhos" -> navToActivity(NotesActivity::class.java)
                "inMinds" -> {
                    navToActivity(PostActivity::class.java)
                }
                "Time Line" -> {
                    navToActivity(FeedActivity::class.java)
                }
            }
            R.id.nav_header_edit -> navToActivity(ProfileActivity::class.java)
        }
    }

    private fun <T> navToActivity(targetActivity: Class<T>) {
        startActivity(Intent(this, targetActivity))
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProfileHeader()
    }
}