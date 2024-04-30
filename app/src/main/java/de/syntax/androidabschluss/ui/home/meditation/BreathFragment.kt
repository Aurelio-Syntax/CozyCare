package de.syntax.androidabschluss.ui.home.meditation

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentBreathBinding
import de.syntax.androidabschluss.ui.home.home.HomeFragmentDirections


class BreathFragment : Fragment() {
    private lateinit var binding: FragmentBreathBinding

    private var timeSelected: Int = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffSet: Long = 0
    private var isStart = true
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private val soundDurationMillis = 3000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreathBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Gif runs in the ImageView gifBreath in the fragment_breath.xml
        val imageView = binding.gifBreath
        Glide.with(this)
            .load(R.drawable.breathe)
            .into(imageView)

        // Button um Zeit zum TImer hinzuzufügen
        binding.btnTimeAdd.setOnClickListener {
            setTimeFunction()
        }
        // Button zum Starten des Timers
        binding.btnPlay.setOnClickListener {
            startTimerSetup()
        }
        // Button zum resetten des Timers
        binding.ibReset.setOnClickListener {
            resetTime()
        }
        // Button zum hinzufügen von 15 Extra Sekunden
        binding.tvAddTime.setOnClickListener {
            addExtraTime()
        }

        // animiertes Icon mit Glide (info Icon Rot)
        val imginfo = binding.imgBreathInfo
        Glide.with(this)
            .load(R.drawable.info_red)
            .into(imginfo)

        binding.imgBreathInfo.setOnClickListener {
            findNavController().navigate(BreathFragmentDirections.actionBreathFragmentToBreathInfoFragment())
        }

    }
    // Wenn aus dem Fragment navigiert wird, werden die Ressourcen des Mediaplayers wieder freigegeben
    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        vibrator = null
    }

    // Funktion zum hinzufügen von 15 Extra Sekunden
    private fun addExtraTime() {
        if (timeSelected != 0) {

            timeSelected += 15
            binding.progressTimer.max = timeSelected
            timePause()
            startTimer(pauseOffSet)
            Toast.makeText(requireContext(), R.string.addTime, Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetTime() {
        if (timeCountDown != null) {

            timeCountDown!!.cancel()
            timeProgress = 0
            timeSelected = 0
            pauseOffSet = 0
            timeCountDown = null
            binding.btnPlay.text = "Start"
            isStart = true
            binding.progressTimer.progress = 0
            binding.tvTimeLeft.text = "0"

        }
    }
    // Button zum pausieren des Timers
    private fun timePause() {
        if (timeCountDown != null) {

            timeCountDown!!.cancel()
        }
    }

    private fun startTimerSetup() {
        if (timeSelected>timeProgress) {

            if (isStart) {
                binding.btnPlay.text = "Pause"
                startTimer(pauseOffSet)
                isStart = false
            } else {
                isStart = true
                binding.btnPlay.text = "Resume"
                timePause()
            }
        } else {
            Toast.makeText(requireContext(), R.string.startTimer, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer(pauseOffSetL : Long) {
        binding.progressTimer.progress = timeProgress

        timeCountDown = object: CountDownTimer(
            (timeSelected*1000).toLong() - pauseOffSetL*1000, 1000)
        {
            override fun onTick(p0: Long) {
                timeProgress++

                pauseOffSet = timeSelected.toLong()- p0/1000

                binding.progressTimer.progress = timeSelected - timeProgress

                binding.tvTimeLeft.text = (timeSelected - timeProgress).toString()
            }

            override fun onFinish() {
                resetTime()
                Toast.makeText(requireContext(), R.string.finish, Toast.LENGTH_SHORT).show()
                playSoundForDuration()
                vibrateForDuration()
            }
        }.start()
    }

    // Funktion zum setzen der Timer Zeit (Dialog mit Eingabefeld wird geöffnet)
    private fun setTimeFunction() {
        val timeDialog = Dialog(requireContext())
        timeDialog.setContentView(R.layout.timer_add_dialog)

        val timeSet = timeDialog.findViewById<EditText>(R.id.et_GetTime)
        timeDialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {

            // Wenn bisher keine Zeit eingegeben wurde
            if (timeSet.text.isEmpty()) {
                Toast.makeText(requireContext(),R.string.setTime,Toast.LENGTH_SHORT).show()
            } else {
                // Sobald Zeit eingegeben wurde
                resetTime()
                binding.tvTimeLeft.text = timeSet.text
                binding.btnPlay.text = "Start"
                timeSelected = timeSet.text.toString().toInt()
                binding.progressTimer.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog.show()
    }
    // Fuktion. die einen Sound abspielt (wird genutzt wenn Timer abgelaufen ist)
    private fun playSoundForDuration() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.timer_alarm_tone)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null}
    }

    // Funktion, die eine Vibration abgibt (wird genutzt sobald Timer abgelaufen ist)
    private fun vibrateForDuration () {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(1000)
        }
        Handler().postDelayed({
            vibrator?.cancel()
        }, soundDurationMillis.toLong())
    }

    /* Wenn der Timer nicht abgelaufen ist und die App auf dem State Destroy ist da geschlossen wurde ect.
    wird der timer auf 0 gesetzt und die reset Funktion genutzt um alles zurückzusetzen.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (timeCountDown != null) {

            resetTime()
        }
    }


}