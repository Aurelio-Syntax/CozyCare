package de.syntax.androidabschluss


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUiSaveStateControl
import de.syntax.androidabschluss.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    @OptIn(NavigationUiSaveStateControl::class) // Hat mit der NavigationUI line zu tun
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHost.navController, false)
        }
    }