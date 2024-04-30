package de.syntax.androidabschluss.ui.home.settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentAppInfoBinding


class AppInfoFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.8f)
    }

    // create a new instance of the fragment
    companion object {
        fun newInstance(image: Int, title: String, text: String, text2: String, text3: String, backgroundColor : Int): AppInfoFragment {
            val fragment = AppInfoFragment()
            val args = Bundle()
            args.putString("image", image.toString())
            args.putString("title", title)
            args.putString("text", text)
            args.putString("text2", text2)
            args.putString("text3", text3)
            args.putInt("backgroundColor", backgroundColor)
            fragment.arguments = args
            return fragment
        }
    }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val versionName = BuildConfig.VERSION_NAME
            val inflater = LayoutInflater.from(requireContext())
            val dialogBinding = FragmentAppInfoBinding.inflate(inflater)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            val image = requireArguments().getString("image", "")
            val title = requireArguments().getString("title", "")
            val text = requireArguments().getString("text", "")
            val text2 = requireArguments().getString("text2", "Version: $versionName")
            val text3 = requireArguments().getString("text3", "")
            val backgroundColor =
                requireArguments().getInt("backgroundColor", R.color.md_theme_light_errorContainer)
            val color = ContextCompat.getColor(requireContext(), backgroundColor)
            dialogBinding.cardView.setBackgroundColor(color)
            dialogBinding.tvTitle.text = title
            dialogBinding.tvCreatedDescription.text = text
            dialogBinding.tvVersion.text = text2
            dialogBinding.tvCreatedYear.text = text3
            dialogBinding.ivCard.setImageResource(image.toInt())


            // width and height of the dialog
            val width =
                resources.displayMetrics.widthPixels * 0.8 // Zum Beispiel 90% der Bildschirmbreite
            val height =
                resources.displayMetrics.heightPixels * 0.36 // Zum Beispiel 80% der Bildschirmhöhe
            dialog.window?.setLayout(width.toInt(), height.toInt())
            // radius for the corners
            val drawable = GradientDrawable()
            drawable.cornerRadius = resources.displayMetrics.density * 20 // Radius in dp umwandeln
            dialog.window?.setBackgroundDrawable(drawable)
            return dialog
        }
    }