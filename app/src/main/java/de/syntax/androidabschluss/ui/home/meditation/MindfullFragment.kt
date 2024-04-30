package de.syntax.androidabschluss.ui.home.meditation

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentMindfullBinding

class MindfullFragment : Fragment() {
    private lateinit var binding: FragmentMindfullBinding
    private lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var sharedPreferences: SharedPreferences
    var mediaPlayer: MediaPlayer = MediaPlayer()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMindfullBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause() // Wiedergabe ist pausiert, wenn der Nutzer zurückkehrt
            binding.btnPlay.setImageResource(R.drawable.ic_play)
        }
    }
    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause() // Wiedergabe wird pausiert, wenn der Nutzer die app im Hintergrund laufen lässt
            binding.btnPlay.setImageResource(R.drawable.ic_play)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        sharedPreferences = requireContext().getSharedPreferences("language_pref", Context.MODE_PRIVATE)

        createMediaPlayer() // Funktion die von weiter unten aufgerufen wird

        binding.mindfullSeekbar.progress = 0    // Standart ist die Seekbar bei 0%
        binding.mindfullSeekbar.max = mediaPlayer.duration //Jenachdem wie lang die Audio ist wird die Seekbar drauf angepasst

        binding.btnPlay.setOnClickListener { // Verschiedene Zustände, damit sich das Icon im Player ändert
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            } else {
                mediaPlayer.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play)
            }
        }
        binding.mindfullSeekbar.setOnSeekBarChangeListener(object :     // Sobald man den Regler der Seekbar bewegt oder irgendwo auf der Seekbar klickt
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, changed: Boolean) {
                if (changed) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        runnable = Runnable {
            binding.mindfullSeekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)         // Wenn die Audio zu ende ist wieder wieder das Play icon dargestellt und die seekbar auf 0% gesetzt
        mediaPlayer.setOnCompletionListener {
            binding.btnPlay.setImageResource(R.drawable.ic_play)
            binding.mindfullSeekbar.progress = 0
        }
    }

    private fun getLanguageFromSharedPreferences(): String {
        return sharedPreferences.getString("selected_language", "de") ?: ""
    }
    // Sobald im SettingsFragment die Sprache geändert wird, wird auch die Sprache der Audio verändert

    private fun createMediaPlayer() {
        val language = getLanguageFromSharedPreferences()
        Log.e("Mindfull", "$language")
        val mediaPlayerId: Int = when (language) {
            "en" -> R.raw.mindful_voice_english
            "de" -> R.raw.mindful_voice
            else -> R.raw.mindful_voice
        }
        mediaPlayer = MediaPlayer.create(requireContext(), mediaPlayerId)
        // Hinzufügen von EIgenschaften des MediaPlayers wie in meinem Fall die Seekbar
        mediaPlayer.setOnPreparedListener {
            // Sobald die Audio bereit ist wird die Seekbar auf die Maximum Laufzeit des Songs eingestellt
            binding.mindfullSeekbar.max = mediaPlayer.duration
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }
}
