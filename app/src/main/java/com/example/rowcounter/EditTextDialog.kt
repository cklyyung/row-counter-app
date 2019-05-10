package com.example.rowcounter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

const val ARG_DIALOG_TYPE = "com.example.rowcounter.DIALOG_TYPE_ARGS"
const val ARG_DIALOG_HINT =  "com.example.rowcounter.DIALOG_ARG_HINT"
const val ARG_DIALOG_POSITION =  "com.example.rowcounter.DIALOG_ARG_POSITION"

class EditTextDialog() : DialogFragment () {

    private lateinit var listener: EditTextDialogListener

    interface EditTextDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, input: String = "null")
        fun onDialogPositiveClick(dialog: DialogFragment, input: String, position: Int = 0)
        fun onDialogPositiveClick(dialog: DialogFragment, input: Int, position: Int = 0)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val dialogLayout = inflater.inflate(R.layout.dialog_user_input, null)
            val dialogTitle = dialogLayout.findViewById<TextView>(R.id.dialog_title)
            val dialogEditText = dialogLayout.findViewById<EditText>(R.id.dialog_edittext)

            val type = arguments?.getInt(ARG_DIALOG_TYPE)

            if (type == 1) {
                dialogTitle.text = (context as ProjectActivity).getString(R.string.counter_name)
                dialogEditText.hint = arguments?.getString(ARG_DIALOG_HINT)
            } else if (type == 2) {
                dialogTitle.text = (context as ProjectActivity).getString(R.string.dialog_repeat_limit)
                dialogEditText.hint = ""
                dialogEditText.inputType = InputType.TYPE_CLASS_NUMBER
            }

            val dialog = builder.setView(dialogLayout)
                .create()

            dialogLayout.findViewById<Button>(R.id.button_ok).setOnClickListener{
                val input = dialogEditText.text.toString()
                if (input.isNullOrEmpty() || (type == 2 && input.startsWith("0"))) {
                    dialogEditText.shake()
                } else {
                    if (type == 0) { // Add Project Name
                        listener.onDialogPositiveClick(this, input)
                    } else if (type == 1){ // Edit Counter Name
                        val position = arguments?.getInt(ARG_DIALOG_POSITION).zeroIfNull()
                        listener.onDialogPositiveClick(this, input, position)
                    } else { // Edit Counter Repeat Limit
                        val position = arguments?.getInt(ARG_DIALOG_POSITION).zeroIfNull()
                        listener.onDialogPositiveClick(this, input.toInt(), position)
                    }
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
            listener = context as EditTextDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement EditTextFragmentListener"))
        }
    }
}