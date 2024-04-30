package de.syntax.androidabschluss.ui.home.dayPlan

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Tasks
import de.syntax.androidabschluss.databinding.FragmentToDoBinding
import de.syntax.androidabschluss.util.ToDoAdapter
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    private lateinit var adapter: ToDoAdapter
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToDoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cozyCareViewModel.tasksList.observe(viewLifecycleOwner) {
            binding.rvToDo.adapter = ToDoAdapter(it, requireContext(), cozyCareViewModel)
        }

        // Navigation zum BottomSheetFragment um dort ein neuen Task zu erstellen/ zu speichern
        binding.floatingActionButtonToDo.setOnClickListener {
            findNavController().navigate(ToDoFragmentDirections.actionToDoFragmentToBottomSheetTaskFragment())
        }


        // Wenn ein Task im Fragment nach links gewischt wird, wird der Task Gelöscht
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val task = Tasks(0, "", false)

                if (direction == ItemTouchHelper.LEFT) {
                    // Bestätiguns AlertDialog zum löschen. Bei Ja wird der Task gelöscht, bei Nein ändert sich nichts
                    AlertDialog.Builder(requireContext())
                        .setMessage("Bist du dir sicher, dass du die Aufgabe löschen möchtest?")
                        .setPositiveButton(R.string.yes) { _, _ ->
                            val taskToDelete = (binding.rvToDo.adapter as ToDoAdapter).getTaskPosition(position)
                            cozyCareViewModel.deleteTask(taskToDelete)
                            (binding.rvToDo.adapter as ToDoAdapter).notifyItemRemoved(position)
                        }
                        .setNegativeButton(R.string.no) { dialog, _ ->
                            (binding.rvToDo.adapter as ToDoAdapter).notifyItemChanged(position)
                            dialog.cancel()
                        }
                        .create()
                        .show()
                    Log.e("TodoFragment", "$position")


                } else {
                    val taskToUpdate = (binding.rvToDo.adapter as ToDoAdapter).getTaskPosition(position)
                    // Task bearbeiten
                    cozyCareViewModel.getTaskById(taskToUpdate.id)
                    (binding.rvToDo.adapter as ToDoAdapter).notifyItemChanged(position)
                    findNavController().navigate(ToDoFragmentDirections.actionToDoFragmentToUpdateTaskFragment(taskToUpdate.id))
                    Log.e("TodoFragment", "${taskToUpdate.id}")
                }
            }
            /* Funktion zum hinzufügen der Farben des Hintergrunds beim swipen
            rechts und links, sowie dessen Icons
             */
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                val icon: Drawable
                val background: ColorDrawable
                val backgroundCornerOffset = 20
                val itemView: View = viewHolder.itemView
                val adapter = binding.rvToDo.adapter as ToDoAdapter

                // Wenn eine bestimmte Position beim swipen erreicht wird, wird das Icon sowie die dazugehörige Farbe sichtbar
                if (dX>0) {
                    icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.icon_edit)!!
                    background = ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.green))
                } else {
                    icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.icon_delete_task)!!
                    background = ColorDrawable(Color.RED)
                }
                val iconMargin: Int = (itemView.height - icon.intrinsicHeight) /2
                val iconTop: Int = itemView.top + (itemView.height - icon.intrinsicHeight) /2
                val iconBottom = iconTop + icon.intrinsicHeight

                /* Wenn eine bestimmte Position beim swipen erreicht wird.
                Treten bindings in Kraft um den Background und die Icons an der angegebenen Stelle zu finden
                 */
                if (dX>0) {
                    val iconLeft = itemView.left + iconMargin
                    val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    background.setBounds(itemView.left, itemView.top,
                        (itemView.left + dX + backgroundCornerOffset).toInt(), itemView.bottom)

                } else if (dX<0) {
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    background.setBounds(
                        (itemView.right + dX - backgroundCornerOffset).toInt(), itemView.top,
                        itemView.right, itemView.bottom)
                }
                else {
                    background.setBounds(0, 0, 0, 0)
                }
                background.draw(c)
                icon.draw(c)
            }
        }
        // ItemtouchHelper kriegt den SimpleCallback zugewiesen und wird dann an die RecyclerView gebunden mit der die Funktion genutzt werden soll
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvToDo)

    }
}