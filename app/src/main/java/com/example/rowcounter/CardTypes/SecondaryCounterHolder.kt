package com.example.rowcounter.CardTypes

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.rowcounter.*

class SecondaryCounterHolder(v: View, n: Int = 0) : CounterHolder(v, n) {

    private var deleteListener: RemoveCardInterface = v.context as RemoveCardInterface

    private val repeatCountString: String = v.context.getString(R.string.count_repeats)
    private val repeatDisplayString: String = v.context.getString(R.string.repeat_counter)

    private val closeButton: ImageView = itemView.findViewById(R.id.close_button)
    private var counterTitle: TextView = itemView.findViewById(R.id.counter_title)
    private var repeatDisplay: TextView = itemView.findViewById(R.id.repeat_counter)
    private var repeatToggle: ImageView = itemView.findViewById(R.id.repeat_toggle)
    private var linkToggle: ImageView = itemView.findViewById(R.id.link_toggle)
    private var repeatToggleState: Int = 0
    private var linkToggleState: Int = 0
    private var position: Int? = null
    private var repeatLimit: Int = 6
    private var repeats: Int = 0


    init {

        closeButton.setOnClickListener{
            deleteListener.OnRemoveButtonClick(position, 1, counterTitle.text.toString())
        }

        linkToggle.setOnClickListener{ v ->
            linkToggleState = if (linkToggleState == 0) 1 else 0

            if (linkToggleState == 0) {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_grey_24dp))
            } else {
                linkToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_link_accent_24dp))
            }

        }

        repeatToggle.setOnClickListener{ v ->
            repeatToggleState = if (repeatToggleState == 0) 1 else 0

            if (repeatToggleState == 0) {
                repeatToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_grey_24dp))
                displayValue += repeats * repeatLimit
                repeatDisplay.visibility = View.INVISIBLE
            } else {
                repeatToggle.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_repeat_accent_24dp))
                repeats = displayValue / repeatLimit
                displayValue = displayValue % repeatLimit
                repeatDisplay.visibility = View.VISIBLE
            }
            updateDisplay()

        }
    }

    override fun updateDisplay() {
        if (repeatToggleState == 1) {
            repeatDisplay.text = repeatCountString.format(repeats)
            display.text = repeatDisplayString.format(displayValue, repeatLimit)
        } else {
            super.updateDisplay()
        }
    }

    fun bindHolder(name: String, position: Int) {
        this.position = position
        display.text = displayValue.toString()
        title.text = name
    }

    override fun add() {
        if (repeatToggleState == 0) {
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
        if (repeatToggleState == 0) {
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