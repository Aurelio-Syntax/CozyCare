package de.syntax.androidabschluss.ui.start


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import de.syntax.androidabschluss.MainActivity
import de.syntax.androidabschluss.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Falls ein Nutzer angemeldet ist, wird dieser direkt zur CarouselActivity und MainActivty weitergeleitet
        falls nicht, wird die normale StartActivity aufgerufen
         */
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // Benutzer ist eingeloggt
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Kein Benutzer gefunden, start der StartActivity
        }


        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}