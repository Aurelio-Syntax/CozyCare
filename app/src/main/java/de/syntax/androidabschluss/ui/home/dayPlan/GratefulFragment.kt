package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentGratefulBinding
import de.syntax.androidabschluss.util.GratefulBookAdapter
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class GratefulFragment : Fragment() {
    private lateinit var binding: FragmentGratefulBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGratefulBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cozyCareViewModel.diaryEntriesList.observe(viewLifecycleOwner) {
            binding.rvGrateful.adapter = GratefulBookAdapter(it, requireContext(), cozyCareViewModel)

            val addImg = binding.imgGratefulFab
            Glide.with(this)
                .load(R.drawable.pen_anim2)
                .into(addImg)
        }

        binding.imgGratefulFab.setOnClickListener {
            findNavController().navigate(GratefulFragmentDirections.actionGratefulFragmentToCreateDiaryEntryFragment())
        }
    }


}