package com.example.rowcounter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText

class CreateCounterDialog : DialogFragment () {

    private lateinit var listener: CreateCounterDialogListener

    interface CreateCounterDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, projectName: String = "null")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            val dialogLayout = inflater.inflate(R.layout.dialog_new_counter, null)
            val projectNameEditText = dialogLayout.findViewById<EditText>(R.id.project_name)

            val dialog = builder.setView(dialogLayout)
                .create()

            dialogLayout.findViewById<Button>(R.id.button_ok).setOnClickListener{
                val projectName = projectNameEditText.text.toString()
                if (projectName.isNullOrEmpty()) {
                    val errorRed = ContextCompat.getColor(dialogLayout.context, R.color.errorRed)
                    projectNameEditText.setHintTextColor(errorRed)
                    projectNameEditText.background.mutate().setColorFilter(errorRed, PorterDuff.Mode.SRC_ATOP)
                    projectNameEditText.startAnimation(
                        AnimationUtils.loadAnimation(this.context, R.anim.shake))

                } else {
                    listener.onDialogPositiveClick(this, projectNameEditText.text.toString())
                    dialog.dismiss()
                }
            }

            dialogLayout.findViewById<Button>(R.id.button_cancel).setOnClickListener{
                dialog.dismiss()
            }

            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Override the Fragment.onAttach() method to instantiate the CreateCounterDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as CreateCounterDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement CreateCounterDialogFragmentListener"))
        }
    }
}