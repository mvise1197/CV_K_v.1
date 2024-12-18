package com.mv.cv_k_v1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mv.cv_k_v1.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toogle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.nav_open,R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        //MENÚ HORIZONTAL
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.mh_inicio -> PrincipalActivity()
                R.id.mh_vercitas -> replaceFragment(VerCitaFragment())
                R.id.mh_verpet -> replaceFragment(VerPetsFragment())
                else  ->{
                }
            }
            true
        }
        fragmentManager = supportFragmentManager
        replaceFragment(VerCitaFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mv_perfil -> replaceFragment(MiPerfilFragment())
            R.id.mv_newcita -> replaceFragment(CrearCitaFragment())
            R.id.mv_newpet -> replaceFragment(CrearPetsFragment())
            R.id.mv_exit ->{
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IniciarSesionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                Toast.makeText(this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show()
                true
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}