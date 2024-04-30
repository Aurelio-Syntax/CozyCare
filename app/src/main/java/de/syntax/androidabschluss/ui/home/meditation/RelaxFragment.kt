package de.syntax.androidabschluss.ui.home.meditation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import de.syntax.androidabschluss.databinding.FragmentRelaxBinding
import de.syntax.androidabschluss.util.YoutubeAdapter
import de.syntax.androidabschluss.viewmodel.YoutubeViewModel


class RelaxFragment : Fragment() {
    private lateinit var binding: FragmentRelaxBinding
    private lateinit var youtubePlayerView: YouTubePlayerView
    private val viewModel: YoutubeViewModel by activityViewModels()
    private var videoId: String = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRelaxBinding.inflate(layoutInflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val youTubePlayerView: YouTubePlayerView = binding.player1
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })


        viewModel.getVideos()
        viewModel.videos.observe(viewLifecycleOwner) {
            Log.e("Fragment", viewModel.videos.toString())
            binding.rvYoutube.adapter = YoutubeAdapter(it, this)

        }
    }
    fun updateVideo(videoId: String) {
        this.videoId = videoId
        val youTubePlayerView: YouTubePlayerView = binding.player1
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}