package com.example.rowcounter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectListRecyclerAdapter(private val projects: ArrayList<String>) : RecyclerView.Adapter<ProjectListRecyclerAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val inflatedView = parent.inflate(R.layout.project_list_item, false)
        return ProjectHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val project = projects[position]
        holder.bindProject(project)
    }


    //1
    inner class ProjectHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var projectName: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("ProjectRecyclerView", "CLICK $projectName")
            val context = v.context
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(EXTRA_PROJECT_NAME, projectName)
            context.startActivity(intent)
        }

        fun bindProject(project: String) {
            this.projectName = project
            view.project_name.text = projectName
        }

    }

}

