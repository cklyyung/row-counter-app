package com.example.rowcounter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.rowcounter.Adapters.CounterRecyclerAdapter
import kotlinx.android.synthetic.main.recycler_view.*
import android.content.DialogInterface



class ProjectActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CounterRecyclerAdapter

    private var cards = ArrayList<Int>()
    private var position: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra(EXTRA_PROJECT_NAME)
        position = intent.getIntExtra(EXTRA_PROJECT_POSITION, 0)

        setContentView(R.layout.activity_project)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        linearLayoutManager = LinearLayoutManager(this)
        recycler_list.layoutManager = linearLayoutManager

        cards.add(0)

        adapter = CounterRecyclerAdapter(cards)
        recycler_list.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_project, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete -> {
                showDeleteDialog()
                return true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete project")
            .setMessage("Are you sure you want to delete this project?")

            .setPositiveButton(android.R.string.yes) { dialog, which ->
                finishAndDelete()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }

    private fun finishAndDelete() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_PROJECT_POSITION, position)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
