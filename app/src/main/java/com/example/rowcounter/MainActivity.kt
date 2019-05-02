package com.example.rowcounter

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*

const val EXTRA_PROJECT_NAME = "com.example.rowcounter.PROJECT_NAME"

class MainActivity : AppCompatActivity(), CreateCounterDialog.CreateCounterDialogListener {

    var projects = ArrayList<String>()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProjectListRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        linearLayoutManager = LinearLayoutManager(this)
        project_list.layoutManager = linearLayoutManager

        adapter = ProjectListRecyclerAdapter(projects)
        project_list.adapter = adapter
        project_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fab.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val confirmCreate = CreateCounterDialog()
        confirmCreate.show(supportFragmentManager, "CreateCounterDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, projectName: String) {
        addProject(projectName)
    }

    private fun addProject(projectName: String) {
        projects.add(projectName)
        adapter.notifyDataSetChanged()
        Snackbar.make(project_list, "$projectName created", Snackbar.LENGTH_SHORT).show()
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
