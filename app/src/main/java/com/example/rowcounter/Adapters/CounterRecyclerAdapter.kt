package com.example.rowcounter.Adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.rowcounter.CardTypes.CardInfo
import com.example.rowcounter.CardTypes.CounterHolder
import com.example.rowcounter.CardTypes.SecondaryCounterHolder
import com.example.rowcounter.R
import com.example.rowcounter.inflate

class CounterRecyclerAdapter(private val cards: ArrayList<CardInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var globalCounter : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val inflatedView = parent.inflate(R.layout.secondary_counter, false)
            return SecondaryCounterHolder(inflatedView)
        } else {
            val inflatedView = parent.inflate(R.layout.main_counter, false)
            return CounterHolder(inflatedView, globalCounter)
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
        if (viewType == 1) {
            (holder as SecondaryCounterHolder).bindHolder(position)
        } else {
            (holder as CounterHolder).bindHolder()
        }
    }
}


