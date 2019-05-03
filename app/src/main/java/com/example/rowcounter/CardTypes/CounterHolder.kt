package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.support.v7.widget.RecyclerView

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.EditTextDialog
import com.example.rowcounter.ProjectActivity
import com.example.rowcounter.R

open class CounterHolder(v: View, n: Int = 0) : RecyclerView.ViewHolder(v) {

    protected val view = v
    protected val minusButton: ImageButton
    protected val display: TextView
    protected val addButton: ImageButton
    protected val clearButton: ImageView
    protected val editButton: ImageView
    protected var displayValue = n

    init {

        display = itemView.findViewById(R.id.display)

        minusButton = itemView.findViewById(R.id.minus_button)
        minusButton.setOnClickListener( {v ->
            if (displayValue > 0) displayValue--
            display.text = displayValue.toString()
        })

        addButton = itemView.findViewById(R.id.add_button)
        addButton.setOnClickListener({v ->
            if (displayValue < 99) displayValue++
            display.text = displayValue.toString()
        })

        clearButton = itemView.findViewById(R.id.clear_counter_button)
        clearButton.setOnClickListener({ v ->
            if (displayValue > 0) showClearDialog()
        })

        editButton = itemView.findViewById(R.id.edit_button)


    }


    fun bindHolder() {
        display.text = displayValue.toString()
    }

    private fun showEditTextDialog() {
        val confirmCreate = EditTextDialog()
        confirmCreate.show((view.context as ProjectActivity).supportFragmentManager, "EditTextDialog")
    }

    open fun showClearDialog() {
        AlertDialog.Builder(view.context)
            .setMessage("Clear counter and all linked counters?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                displayValue = 0
                display.text = displayValue.toString()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }
}