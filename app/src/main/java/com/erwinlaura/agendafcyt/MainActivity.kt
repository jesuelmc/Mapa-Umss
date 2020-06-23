package com.erwinlaura.agendafcyt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.erwinlaura.agendafcyt.Admin.AdminFragment
import com.erwinlaura.agendafcyt.Maps.MapFragment.MapFragment
import com.erwinlaura.agendafcyt.PresentationActivity.PresentationActivity
import com.erwinlaura.agendafcyt.PresentationActivity.PresentationFragment

import com.erwinlaura.agendafcyt.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var currentFragment: Fragment

    private lateinit var binding: ActivityMainBinding


    companion object {
        const val REQUEST_CODE_SEARCHLOCATION = 3243
        const val REQUEST_CODE_GET_IMAGE = 234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        checkIfFirstExecution()

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            fragmentTransaction(MapFragment())
            binding.navigation.menu.getItem(0).isChecked = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu?.let {
            inflateNavigationDrawer(it)
        }
        return super.onCreateOptionsMenu(menu)
    }


    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.map_fragment_container, fragment)
            .commit()
    }

    private fun checkIfFirstExecution() {
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            //Is first execution?
            if (!getBoolean(PresentationFragment.COMPLETED_PRESENTATION, false)) {
                startActivity(Intent(this@MainActivity, PresentationActivity::class.java))
            }
        }
    }

    private fun inflateNavigationDrawer(menu: Menu) {

        val toggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.mainToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        toggle.isDrawerIndicatorEnabled = true
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigation.setNavigationItemSelectedListener(this)

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_map -> fragmentTransaction(MapFragment())
            R.id.nav_admin -> fragmentTransaction(AdminFragment())

        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)

        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {

        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) binding.mainDrawerLayout.closeDrawer(
            GravityCompat.START
        )
        else super.onBackPressed()
    }


}
