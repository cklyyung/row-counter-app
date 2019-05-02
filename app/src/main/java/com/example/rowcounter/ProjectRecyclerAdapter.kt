package com.example.rowcounter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class ProjectRecyclerAdapter() : RecyclerView.Adapter<ProjectRecyclerAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val inflatedView = parent.inflate(R.layout.main_counter, false)
        return ProjectHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        holder.bindProject()
    }


    //1
    inner class ProjectHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val minusButton: ImageButton
        private val display: TextView
        private val addButton: ImageButton
        private var displayValue = 0

        init {
            minusButton = itemView.findViewById(R.id.minus_button)
            minusButton.setOnClickListener(this)
            display = itemView.findViewById(R.id.display)
            addButton = itemView.findViewById(R.id.add_button)
            addButton.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("ProjectRecyclerView", "CLICK $v")

            if (v == minusButton && displayValue > 0) {
                displayValue--
            } else if (v == addButton && displayValue < 99){
                displayValue++
            }
            display.text = displayValue.toString()

        }

        fun bindProject() {
            display.text = displayValue.toString()
        }
    }

}


