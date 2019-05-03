package com.example.rowcounter.CardTypes

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.R

class SecondaryCounterHolder(v: View, n: Int = 0) : RecyclerView.ViewHolder(v) {

    private lateinit var listener: RemoveCardInterface

    private val minusButton: ImageButton
    private val display: TextView
    private val addButton: ImageButton
    private val closeButton: ImageView
    private var position: Int? = null
    private var displayValue = 0

    init {

        listener = v.context as RemoveCardInterface

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

        closeButton = itemView.findViewById(R.id.close_button)
        closeButton.setOnClickListener({ v ->
            listener.OnRemoveButtonClick(position)
        })

    }

    fun bindHolder(position: Int) {
        this.position = position
        display.text = displayValue.toString()
    }

}