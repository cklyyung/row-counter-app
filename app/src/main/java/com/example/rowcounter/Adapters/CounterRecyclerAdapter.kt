package com.example.rowcounter.Adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.rowcounter.CardTypes.CardInfo
import com.example.rowcounter.CardTypes.CounterCardInfo
import com.example.rowcounter.CardTypes.CounterHolder
import com.example.rowcounter.CardTypes.SecondaryCounterHolder
import com.example.rowcounter.R
import com.example.rowcounter.inflate

class CounterRecyclerAdapter(private val cards: ArrayList<CardInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val inflatedView = parent.inflate(R.layout.main_counter, false)
            val view = CounterHolder(inflatedView)
            view
        } else {
            val inflatedView = parent.inflate(R.layout.secondary_counter, false)
            val view = SecondaryCounterHolder(inflatedView)
            view
        }
    }

    override fun getItemViewType(position: Int): Int {
        return cards[position].cardType
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == 0) {
            (holder as CounterHolder).bindHolder(cards[position])
        } else {
            (holder as SecondaryCounterHolder).bindHolder(cards[position])
        }
    }
}


