package com.example.rowcounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*

class ProjectActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProjectRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra(EXTRA_PROJECT_NAME)

        setContentView(R.layout.activity_project)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayoutManager = LinearLayoutManager(this)
        project_list.layoutManager = linearLayoutManager

        adapter = ProjectRecyclerAdapter()
        project_list.adapter = adapter

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
