package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.*

class SecondaryCounterHolder(v: View) : CounterHolder(v) {

    private var deleteListener: RemoveCardInterface = v.context as RemoveCardInterface

    private val repeatCountString: String = v.context.getString(R.string.count_repeats)
    private val repeatDisplayString: String = v.context.getString(R.string.repeat_counter)

    private val closeButton: ImageView = itemView.findViewById(R.id.close_button)
    private var counterTitle: TextView = itemView.findViewById(R.id.counter_title)
    private var repeatDisplay: TextView = itemView.findViewById(R.id.repeat_counter)
    private var repeatToggle: ImageView = itemView.findViewById(R.id.repeat_toggle)
    private var linkToggle: ImageView = itemView.findViewById(R.id.link_toggle)
    private var repeatToggleState: Boolean = false
    private var linkToggleState: Boolean = false
    private var repeatLimit: Int = -1
    private var repeats: Int = 0


    init {

        closeButton.setOnClickListener{
            deleteListener.OnRemoveButtonClick(position, 1, counterTitle.text.toString())
        }

        linkToggle.setOnClickListener{ v ->
            linkToggleState = if (linkToggleState) false else true

            if (linkToggleState) {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_grey_24dp))
            } else {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_accent_24dp))
            }

        }

        repeatToggle.setOnClickListener{ v ->
            if (!repeatToggleState) {
                showEditRepeatLimitDialog()
            } else {
                toggleRepeatViews(false)
                updateDisplay()
            }

        }
    }

    override fun updateDisplay() {
        if (repeatToggleState) {
            repeatDisplay.text = repeatCountString.format(repeats)
            display.text = repeatDisplayString.format(displayValue, repeatLimit)
        } else {
            super.updateDisplay()
        }
    }

    private fun toggleRepeatViews(on: Boolean) {
        if (on) {
            repeatToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_accent_24dp))
            repeats = displayValue / repeatLimit
            displayValue %= repeatLimit
            repeatDisplay.visibility = View.VISIBLE
        } else {
            repeatToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_grey_24dp))
            displayValue += repeats * repeatLimit
            repeatDisplay.visibility = View.INVISIBLE
            repeatToggleState = false
            repeatLimit = -1
        }
    }

    override fun bindHolder(c: CardInfo) {
        super.bindHolder(c)
        c as SecondaryCounterCardInfo
        this.linkToggleState = c.isLinked
        val oldRepeatToggleState = this.repeatToggleState
        this.repeatToggleState = c.repeatLimit != -1
        this.repeatLimit = c.repeatLimit

        if (oldRepeatToggleState != repeatToggleState) {
            toggleRepeatViews(true)
        }

        updateDisplay()
    }

    override fun add() {
        if (!repeatToggleState) {
            super.add()
        } else {
            if (displayValue == repeatLimit - 1) {
                displayValue = 0
                repeats++
            } else {
                displayValue = Math.min(99, displayValue + 1)
            }
            updateDisplay()
        }
    }

    override fun minus() {
        if (!repeatToggleState) {
            super.minus()
        } else {
            if (displayValue == 0) {
                displayValue = repeatLimit - 1
                repeats--
            } else {
                displayValue = Math.max(0, displayValue - 1)
            }
            updateDisplay()
        }
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

    fun showEditRepeatLimitDialog() {
        val editRepeatLimitDialog = EditTextDialog()
        var args = Bundle()
        args.putInt(ARG_DIALOG_TYPE, 2)
        args.putString(ARG_DIALOG_HINT, title.text.toString())
        args.putInt(ARG_DIALOG_POSITION, adapterPosition)
        editRepeatLimitDialog.arguments = args
        editRepeatLimitDialog.show((itemView.context as ProjectActivity).supportFragmentManager, "EditTextDialogFragment")
    }
}