package com.example.rowcounter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_new_counter.*

class CreateCounterDialogFragment : DialogFragment () {

    private lateinit var listener: CreateCounterDialogFragmentListener

    interface CreateCounterDialogFragmentListener {
        fun onDialogPositiveClick(dialog: DialogFragment, projectName: String = "null")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            val dialogLayout = inflater.inflate(R.layout.dialog_new_counter, null)
            val projectNameEditText = dialogLayout.findViewById<EditText>(R.id.project_name)

            val dialog = builder.setView(dialogLayout)
                .create()

            dialogLayout.findViewById<Button>(R.id.button_ok).setOnClickListener{
                val projectName = projectNameEditText.text.toString()
                if (projectName.isNullOrEmpty()) {
                    Toast.makeText(this.context, R.string.invalid_name, Toast.LENGTH_SHORT).show()
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

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as CreateCounterDialogFragmentListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement CreateCounterDialogFragmentListener"))
        }
    }
}