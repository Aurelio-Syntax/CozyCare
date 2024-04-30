package de.syntax.androidabschluss.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import de.syntax.androidabschluss.MainActivity
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.util.CarouselAdapter
import de.syntax.androidabschluss.data.model.AppPreview
import de.syntax.androidabschluss.databinding.ActivityCarouselBinding

class CarouselActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarouselBinding
    private lateinit var btn_start: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarouselBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Liste der Preview Images vom Carousel + Name
        val AppPreviewList = ArrayList<AppPreview>()
        AppPreviewList.add(AppPreview(R.drawable.homescreen, ""))
        AppPreviewList.add(AppPreview(R.drawable.dayplandetail, ""))
        AppPreviewList.add(AppPreview(R.drawable.notes, ""))
        AppPreviewList.add(AppPreview(R.drawable.meditationscreen, ""))
        AppPreviewList.add(AppPreview(R.drawable.todo_screen, ""))

        val adapter = CarouselAdapter(AppPreviewList)

        // Eigenschaften des Carousels
        binding.apply {
            carouselRecyclerview.adapter = adapter
            carouselRecyclerview.set3DItem(true)
            carouselRecyclerview.setAlpha(true)
            carouselRecyclerview.setInfinite(false)
            carouselRecyclerview.setIntervalRatio(0.7f)
        }

        // Logik des Start Buttons vom HomeScreen
        btn_start = findViewById(R.id.btn_start)
        binding.btnStart.setOnClickListener {
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
        }
    }
}