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
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import com.example.rowcounter.CardTypes.CardInfo
import com.example.rowcounter.CardTypes.RemoveCardInterface
import kotlinx.android.synthetic.main.activity_project.*


class ProjectActivity : AppCompatActivity(), RemoveCardInterface, EditTextDialog.EditTextDialogListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CounterRecyclerAdapter

    private var cards = ArrayList<CardInfo>()
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

        cards.add(CardInfo(0, getString(R.string.main_counter)))

        adapter = CounterRecyclerAdapter(cards)
        recycler_list.adapter = adapter

        fab_menu.setOnClickListener{ v ->
            cards.add(CardInfo(1, getString(R.string.secondary_counter)))
            adapter.notifyDataSetChanged()
            Snackbar.make(recycler_list, "Secondary Counter Created", Snackbar.LENGTH_SHORT).show()
        }

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

    override fun OnRemoveButtonClick(position: Int?, type: Int, name: String) {
        if (position != null) {
            cards.removeAt(position)
            adapter.notifyDataSetChanged()
            Snackbar.make(recycler_list, "$name removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", View.OnClickListener {
                    cards.add(position, CardInfo(type, name))
                    adapter?.notifyDataSetChanged()
                }).show()
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, projectName: String, position: Int) {
        cards[position].cardName = projectName
        adapter.notifyDataSetChanged()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, projectName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
