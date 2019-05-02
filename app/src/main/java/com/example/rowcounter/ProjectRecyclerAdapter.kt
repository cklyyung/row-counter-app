package com.example.rowcounter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectRecyclerAdapter(private val projects: ArrayList<String>) : RecyclerView.Adapter<ProjectRecyclerAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectRecyclerAdapter.ProjectHolder {
        val inflatedView = parent.inflate(R.layout.project_list_item, false)
        return ProjectHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectRecyclerAdapter.ProjectHolder, position: Int) {
        val project = projects[position]
        holder.bindProject(project)
    }


    //1
    class ProjectHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var projectName: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("ProjectRecyclerView", "CLICK $projectName")
        }

        fun bindProject(project: String) {
            this.projectName = project
            view.project_name.text = projectName
        }

    }

}

