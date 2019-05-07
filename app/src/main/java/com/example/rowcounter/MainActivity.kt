package com.example.rowcounter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.rowcounter.Adapters.ProjectListRecyclerAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*

const val EXTRA_PROJECT_NAME = "com.example.rowcounter.PROJECT_NAME"
const val EXTRA_PROJECT_POSITION = "com.example.rowcounter.PROJECT_POSITION"
const val DELETED_REQUEST = 1

class MainActivity : AppCompatActivity(), EditTextDialog.EditTextDialogListener {
    override fun onDialogPositiveClick(dialog: DialogFragment, projectName: String, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var projects = ArrayList<String>()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProjectListRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        linearLayoutManager = LinearLayoutManager(this)
        recycler_list.layoutManager = linearLayoutManager

        adapter = ProjectListRecyclerAdapter(projects)
        recycler_list.adapter = adapter
        recycler_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DELETED_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val deletedProject = data?.getIntExtra(EXTRA_PROJECT_POSITION, -1)
                if (deletedProject != null && deletedProject > -1) {
                    removeProject(deletedProject)
                }

            }
        }
    }

    private fun showDialog() {
        val confirmCreate = EditTextDialog()
        var args = Bundle()
        args.putInt(ARG_DIALOG_TYPE, 0)
        confirmCreate.arguments = args
        confirmCreate.show(supportFragmentManager, "EditTextDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, projectName: String) {
        addProject(projectName)
    }

    private fun addProject(projectName: String) {
        projects.add(projectName)
        adapter.notifyDataSetChanged()
        Snackbar.make(recycler_list, "$projectName created", Snackbar.LENGTH_SHORT).show()
    }

    private fun removeProject(projectPosition: Int) {
        val projectName = projects[projectPosition]
        projects.removeAt(projectPosition)
        adapter.notifyDataSetChanged()
        Snackbar.make(recycler_list, "$projectName removed", Snackbar.LENGTH_LONG)
            .setAction("Undo", View.OnClickListener {
                projects.add(projectPosition, projectName)
                adapter?.notifyDataSetChanged()
            }).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                showDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
