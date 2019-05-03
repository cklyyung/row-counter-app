package com.example.rowcounter.CardTypes

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.rowcounter.R

class MainCounterHolder(v: View, n: Int) : RecyclerView.ViewHolder(v) {

    private val minusButton: ImageButton
    private val display: TextView
    private val addButton: ImageButton
    private var displayValue = n

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
    }

    fun bindHolder() {
        display.text = displayValue.toString()
    }
}