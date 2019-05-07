package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.*

class SecondaryCounterHolder(v: View, n: Int = 0) : CounterHolder(v, n) {

    private var deleteListener: RemoveCardInterface

    private val closeButton: ImageView
    private var counterTitle: TextView
    private var repeatDisplay: TextView
    private var cycleToggle: ImageView
    private var linkToggle: ImageView
    private var cycleToggleState: Int = 0
    private var linkToggleState: Int = 0
    private var position: Int? = null
    private var cycleLimit: Int = 6
    private var repeats: Int = 0


    init {

        deleteListener = v.context as RemoveCardInterface

        counterTitle = itemView.findViewById(R.id.counter_title)
        repeatDisplay = itemView.findViewById(R.id.repeat_counter)

        closeButton = itemView.findViewById(R.id.close_button)
        closeButton.setOnClickListener{
            deleteListener.OnRemoveButtonClick(position, 1, counterTitle.text.toString())
        }

        linkToggle = itemView.findViewById(R.id.link_toggle)
        linkToggle.setOnClickListener{ v ->
            linkToggleState = if (linkToggleState == 0) 1 else 0

            if (linkToggleState == 0) {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_grey_24dp))
            } else {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_accent_24dp))
            }

        }

        cycleToggle = itemView.findViewById(R.id.cycle_toggle)
        cycleToggle.setOnClickListener{ v ->
            cycleToggleState = if (cycleToggleState == 0) 1 else 0

            if (cycleToggleState == 0) {
                cycleToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_grey_24dp))
                displayValue += repeats * cycleLimit
                display.text = displayValue.toString()
                repeatDisplay.visibility = View.INVISIBLE
            } else {
                cycleToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_accent_24dp))

                repeats = displayValue / cycleLimit
                displayValue = displayValue % cycleLimit

                repeatDisplay.visibility = View.VISIBLE
                display.text = displayValue.toString()
                repeatDisplay.text = "$repeats repeats completed"
            }

        }
    }

    fun bindHolder(name: String, position: Int) {
        this.position = position
        display.text = displayValue.toString()
        title.setText(name)
    }

    override fun add() {
        if (cycleToggleState == 0) {
            super.add()
        } else {
            if (displayValue == cycleLimit - 1) {
                displayValue = 0
                repeats++
                repeatDisplay.text = "$repeats repeats completed"
            } else {
                displayValue++
            }
            display.text = displayValue.toString()
        }
    }

    override fun minus() {
        if (cycleToggleState == 0) {
            super.minus()
        } else {
            if (displayValue == 0) {
                displayValue = cycleLimit - 1
                repeats--
                repeatDisplay.text = "$repeats repeats completed"
            } else {
                displayValue--
            }
            display.text = displayValue.toString()
        }

    }

    override fun showEditTitleDialog() {
        val confirmCreate = EditTextDialog()
        var args = Bundle()
        args.putInt(ARG_DIALOG_TYPE, 1)
        args.putString(ARG_DIALOG_HINT, title.text.toString())
        args.putInt(ARG_DIALOG_POSITION, position.zeroIfNull())
        confirmCreate.arguments = args
        confirmCreate.show((itemView.context as ProjectActivity).supportFragmentManager, "EditTextDialogFragment")
    }

    override fun showClearDialog() {
        AlertDialog.Builder(view.context)
            .setMessage("Clear counter?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                displayValue = 0
                display.text = displayValue.toString()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }
}