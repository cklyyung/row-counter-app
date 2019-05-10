package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.RecyclerView

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.*

open class CounterHolder(v: View) : RecyclerView.ViewHolder(v) {

    protected val view = v
    private val minusButton: ImageButton = itemView.findViewById(R.id.minus_button)
    protected val display: TextView = itemView.findViewById(R.id.display)
    private val addButton: ImageButton = itemView.findViewById(R.id.add_button)
    private val clearButton: ImageView = itemView.findViewById(R.id.clear_counter_button)
    private val editButton: ImageView = itemView.findViewById(R.id.edit_button)
    protected var title: TextView = itemView.findViewById(R.id.counter_title)
    protected var displayValue: Int = 0
    protected var firstTimeLoad: Boolean = true

    init {

        minusButton.setOnClickListener{
            minus()
        }

        addButton.setOnClickListener{
            add()
        }

        clearButton.setOnClickListener{
            if (displayValue > 0) showClearDialog()
        }

        editButton.setOnClickListener{
            showEditTitleDialog()
        }

    }

    open fun bindHolder(c: CardInfo) {
        c as CounterCardInfo
        if (firstTimeLoad) {
            firstTimeLoad = false
            displayValue = c.displayValue
        }
        title.text = c.cardName
        updateDisplay()
    }

    open fun updateDisplay() {
        display.text = displayValue.toString()
    }

    open fun add() {
        if (displayValue < 99) {
            displayValue++
            updateDisplay()
        }
    }

    open fun minus() {
        if (displayValue > 0) {
            displayValue--
            updateDisplay()
        }
    }

    open fun showEditTitleDialog() {
        val confirmCreate = EditTextDialog()
        var args = Bundle()
        args.putInt(ARG_DIALOG_TYPE, 1)
        args.putString(ARG_DIALOG_HINT, title.text.toString())
        args.putInt(ARG_DIALOG_POSITION, adapterPosition)
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