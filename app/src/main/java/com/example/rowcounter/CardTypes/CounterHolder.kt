package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.InputType

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.*

open class CounterHolder(v: View, n: Int = 0) : RecyclerView.ViewHolder(v) {

    protected val view = v
    protected val minusButton: ImageButton
    protected val display: TextView
    protected val addButton: ImageButton
    protected val clearButton: ImageView
    protected val editButton: ImageView
    protected var title: TextView
    protected var displayValue = n

    init {
        display = itemView.findViewById(R.id.display)

        minusButton = itemView.findViewById(R.id.minus_button)
        minusButton.setOnClickListener{
            minus()
        }

        addButton = itemView.findViewById(R.id.add_button)
        addButton.setOnClickListener{
            add()
        }

        clearButton = itemView.findViewById(R.id.clear_counter_button)
        clearButton.setOnClickListener{
            if (displayValue > 0) showClearDialog()
        }

        title = itemView.findViewById(R.id.counter_title)

        editButton = itemView.findViewById(R.id.edit_button)
        editButton.setOnClickListener{
            showEditTitleDialog()
        }

    }

    fun bindHolder(name: String) {
        display.text = displayValue.toString()
        title.text = name
    }

    open fun add() {
        if (displayValue < 99) displayValue++
        display.text = displayValue.toString()
    }

    open fun minus() {
        if (displayValue > 0) displayValue--
        display.text = displayValue.toString()
    }

    open fun showEditTitleDialog() {
        val confirmCreate = EditTextDialog()
        var args = Bundle()
        args.putInt(ARG_DIALOG_TYPE, 1)
        args.putString(ARG_DIALOG_HINT, title.text.toString())
        args.putInt(ARG_DIALOG_POSITION, 0)
        confirmCreate.arguments = args
        confirmCreate.show((itemView.context as ProjectActivity).supportFragmentManager, "EditTextDialogFragment")
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