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
import android.widget.TextView

const val ARG_DIALOG_TYPE = "com.example.rowcounter.DIALOG_TYPE_ARGS"
const val ARG_DIALOG_HINT =  "com.example.rowcounter.DIALOG_ARG_HINT"
const val ARG_DIALOG_POSITION =  "com.example.rowcounter.DIALOG_ARG_POSITION"

class EditTextDialog() : DialogFragment () {

    private lateinit var listener: EditTextDialogListener

    interface EditTextDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, projectName: String = "null")
        fun onDialogPositiveClick(dialog: DialogFragment, projectName: String, position: Int = 0)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val dialogLayout = inflater.inflate(R.layout.dialog_edittext, null)
            val dialogTitle = dialogLayout.findViewById<TextView>(R.id.dialog_title)
            val projectNameEditText = dialogLayout.findViewById<EditText>(R.id.project_name)

            val type = arguments?.getInt(ARG_DIALOG_TYPE)

            if (type == 1) {
                dialogTitle.text = (context as ProjectActivity).getString(R.string.counter_name)
                projectNameEditText.hint = arguments?.getString(ARG_DIALOG_HINT)
            }

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
                    if (type == 0) {
                        listener.onDialogPositiveClick(this, projectNameEditText.text.toString())
                    } else {
                        val position = arguments?.getInt(ARG_DIALOG_POSITION).zeroIfNull()
                        listener.onDialogPositiveClick(this, projectNameEditText.text.toString(), position)
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