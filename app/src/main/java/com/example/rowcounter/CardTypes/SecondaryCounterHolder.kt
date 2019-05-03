package com.example.rowcounter.CardTypes

import android.app.AlertDialog
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
    private var position: Int? = null


    init {

        deleteListener = v.context as RemoveCardInterface

        closeButton = itemView.findViewById(R.id.close_button)
        closeButton.setOnClickListener({ v ->
            deleteListener.OnRemoveButtonClick(position)
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