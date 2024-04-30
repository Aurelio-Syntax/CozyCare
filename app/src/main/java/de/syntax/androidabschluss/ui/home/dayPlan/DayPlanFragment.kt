package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.util.DayPlanAdapter
import de.syntax.androidabschluss.util.DayPlanAdapterSecond
import de.syntax.androidabschluss.data.model.AppPreview
import de.syntax.androidabschluss.data.model.MeditationPreview
import de.syntax.androidabschluss.databinding.FragmentDayPlanBinding


class DayPlanFragment : Fragment() {
    private lateinit var binding: FragmentDayPlanBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDayPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Einstellung des 1. Carousels
        val recyclerView = binding.dayPlanCarousel1
        val appPreviewList = ArrayList<AppPreview>()
        recyclerView.adapter = DayPlanAdapter(appPreviewList)
        appPreviewList.add(AppPreview(R.drawable.dayroutine, ""))
        appPreviewList.add(AppPreview(R.drawable.healthyfood, ""))
        // Einstellung des 2. Carousels
        val recyclerView2 = binding.dayPlanCarousel2
        val meditationPreviewList = ArrayList<MeditationPreview>()
        recyclerView2.adapter = DayPlanAdapterSecond(meditationPreviewList)
        meditationPreviewList.add(MeditationPreview(R.drawable.enjoy2, ""))
        meditationPreviewList.add(MeditationPreview(R.drawable.dankbarkeit2, ""))
        meditationPreviewList.add(MeditationPreview(R.drawable.dankbarkeit, ""))

        val adapter = DayPlanAdapterSecond(meditationPreviewList)

        // Eigenschaften des Carousels
        binding.apply {
            dayPlanCarousel2.adapter = adapter
            dayPlanCarousel2.set3DItem(false)
            dayPlanCarousel2.setAlpha(true)
            dayPlanCarousel2.setInfinite(false)
            dayPlanCarousel2.setFlat(false)
        }
    }
}




