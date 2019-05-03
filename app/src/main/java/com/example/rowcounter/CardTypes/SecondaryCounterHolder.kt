package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.R

class SecondaryCounterHolder(v: View, n: Int = 0) : CounterHolder(v, n) {

    private var deleteListener: RemoveCardInterface

    private val closeButton: ImageView
    private var counterTitle: TextView
    private var cycleToggle: ImageView
    private var linkToggle: ImageView
    private var cycleToggleState: Int = 0
    private var linkToggleState: Int = 0
    private var position: Int? = null


    init {

        deleteListener = v.context as RemoveCardInterface

        counterTitle = itemView.findViewById(R.id.counter_title)

        closeButton = itemView.findViewById(R.id.close_button)
        closeButton.setOnClickListener({ v ->
            deleteListener.OnRemoveButtonClick(position, 1, counterTitle.text.toString())
        })

        linkToggle = itemView.findViewById(R.id.link_toggle)
        linkToggle.setOnClickListener({ v ->
            linkToggleState = if (linkToggleState == 0) 1 else 0

            if (linkToggleState == 0) {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_grey_24dp))
            } else {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_accent_24dp))
            }

        })

        cycleToggle = itemView.findViewById(R.id.cycle_toggle)
        cycleToggle.setOnClickListener({ v ->
            cycleToggleState = if (cycleToggleState == 0) 1 else 0

            if (cycleToggleState == 0) {
                cycleToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_grey_24dp))
            } else {
                cycleToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_accent_24dp))
            }

        })
    }

    fun bindHolder(position: Int) {
        this.position = position
        display.text = displayValue.toString()
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